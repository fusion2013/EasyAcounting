package com.fusion.ea.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fusion.ea.entity.Item;
import com.fusion.ea.entity.File;
import com.fusion.ea.entity.User;
import com.fusion.ea.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private SessionService sessionService;
	
	public Item findOne(int id) throws Exception {
		return itemRepository.findOne(id);
	}
	
	public List<Item> findAll() throws Exception {
		File file = sessionService.getSelectedFile();
		return itemRepository.findByFileAndDeleted(file,false);
	}

	public Item save(Item item) throws Exception {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		item.setCreatedAt(new Date());
		item.setCreatedBy(user);
		item.setFile(sessionService.getSelectedFile());
		return itemRepository.save(item);
	}
	
	public Item update(Item item) throws Exception {
		Item oldItem = itemRepository.findOne(item.getId());
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		oldItem.setName(item.getName());
		oldItem.setGoods(item.isGoods());
		oldItem.setOpeningQty(item.getOpeningQty());
		oldItem.setOpeningValue(item.getOpeningValue());
		oldItem.setPurchaseRate(item.getPurchaseRate());
		oldItem.setPurchaseVat(item.getPurchaseVat());
		oldItem.setSaleRate(item.getSaleRate());
		oldItem.setSaleVat(item.getSaleVat());
		
		oldItem.setModifiedAt(new Date());
		oldItem.setModifiedBy(user);
		return itemRepository.save(oldItem);
	}
	
	public Item updateForTag(Item item) throws Exception {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		item.setModifiedAt(new Date());
		item.setModifiedBy(user);
		return itemRepository.save(item);
	}
	
	public void delete(Item item) throws Exception {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		item.setDeleted(true);
		item.setDeletedAt(new Date());
		item.setDeletedBy(user);
		itemRepository.save(item);
	}

	public Item findByName(String name) throws Exception {
		// TODO Auto-generated method stub
		File file = sessionService.getSelectedFile();
		return itemRepository.findByNameAndFileAndDeleted(name, file, false);
	}
	
}
