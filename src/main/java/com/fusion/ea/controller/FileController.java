package com.fusion.ea.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fusion.ea.entity.File;
import com.fusion.ea.entity.User;
import com.fusion.ea.service.FileService;
import com.fusion.ea.service.SessionService;
import com.fusion.ea.validators.FileValidator;

@Controller
public class FileController {

	@Autowired
	private FileService fileService;

	@Autowired
	private MessageSource msgSource;
	
	@Autowired
	private SessionService sessionService;

	@RequestMapping("/master/file/select")
	public String manageOnlyFile(Model model) {
		File sFile = sessionService.getSelectedFile();
		try {
			User user = sessionService.getLoggedInUser();
			model.addAttribute("files",
					fileService.findByCompany(user.getCompany()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (sFile != null) {
			return "dashboard";
		} else {
			return "master.file.select";
		}
	}

	@RequestMapping("/master/file/manage")
	public String manageFile(Model model) {
		try {
			User user = sessionService.getLoggedInUser();
			model.addAttribute("files",
					fileService.findByCompany(user.getCompany()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.file.manage";
	}

	@RequestMapping("/master/file/save")
	public @ResponseBody String saveOrUpdateFile(@RequestParam("id") String id,
			@RequestParam("name") String name,
			@RequestParam("companyName") String companyName) {
		JSONObject object = new JSONObject();
		try {

			//prepare file as it is not model driven
			File file = null;
			if (id.equals("undefined")) {
				file = new File();
			} else {
				file = fileService.findById(Integer.parseInt(id));
			}
			file.setName(name);
			file.setCompanyName(companyName);
			User user = sessionService.getLoggedInUser();
			file.setCompany(user.getCompany());

			//validate
			FileValidator validator = new FileValidator(fileService);
			DataBinder binder = new DataBinder(file);
			binder.setValidator(validator);
			binder.validate();
			BindingResult result = binder.getBindingResult();
			if (result.hasErrors()) {//wrap errors
				object.put("say", "invalid-inputs");
				for (FieldError err : result.getFieldErrors()) {
					object.put(err.getField(), msgSource.getMessage(
							err.getCode(), null, Locale.US));
				}
			} else {
				file = fileService.save(file);
				object.put("say", "ok");
				object.put("id", file.getId().toString());

				//set file as selected in session
				File sFile = sessionService.getSelectedFile();
				if (sFile.getId().equals(file.getId())) {
					sessionService.setSelectedFile(sFile);
				}
			}
		} catch (Exception e) {
			object.put("say", "error");
			e.printStackTrace();
		}
		return object.toString();
	}

	@RequestMapping("/master/file/delete/{id}")
	public @ResponseBody String deleteFile(@PathVariable int id,
			HttpSession session) {
		JSONObject object = new JSONObject();
		try {
			File file = fileService.findById(id);
			File sFile = sessionService.getSelectedFile();
			if (sFile.getId().equals(file.getId())) {
				session.removeAttribute("selectedFile");
			}
			fileService.delete(file);
			object.put("say", "ok");
		} catch (Exception e) {
			e.printStackTrace();
			object.put("say", "error");
		}
		return object.toString();
	}

	@RequestMapping("/master/file/select/{id}")
	public @ResponseBody String selectFile(@PathVariable int id,
			HttpSession session) {
		JSONObject object = new JSONObject();
		try {
			session.setAttribute("selectedFile", fileService.findById(id));
			object.put("say", "ok");
		} catch (Exception e) {
			e.printStackTrace();
			object.put("say", "error");
		}
		return object.toString();
	}

}
