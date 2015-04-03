<%@ include file="layout/taglib.jsp"%>
<div class="container">
	<!-- start: PAGE HEADER -->
	<div class="row">
		<div class="col-sm-12">
			<!-- start: PAGE TITLE & BREADCRUMB -->
			<div class="page-header">
				<h1>
					User Profile <small>user profile page</small>
				</h1>
			</div>
			<!-- end: PAGE TITLE & BREADCRUMB -->
		</div>
	</div>
	<!-- end: PAGE HEADER -->
	<!-- start: PAGE CONTENT -->
	<div class="row">
		<div class="col-sm-12">
			<div class="tabbable">
				<div class="tab-content">

					<div id="panel_edit_account" class="tab-pane in active">
						<form:form commandName="user">
							<div class="row">
								<div class="col-md-12">
									<h3>Account Info</h3>
									<hr>
								</div>
								<div class="col-md-12" id="invalidCred">
									<div class="errorHandler alert alert-danger">
										<i class="fa fa-remove-sign"></i>You have some form errors. Please check below.
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label"> Username </label>
										<label class="form-control">
										<b>${user.userName}</b>
										</label>
									</div>
									<div class="form-group">
										<label class="control-label"> First Name* </label>
										<form:input class="form-control" id="firstname"
											path="firstName" />
										<span class="help-block"><form:errors path="firstName" />
										</span>
									</div>
									<div class="form-group">
										<label class="control-label"> Last Name* </label>
										<form:input class="form-control" id="lastname" path="lastName" />
										<span class="help-block"><form:errors path="lastName" />
										</span>
									</div>
									<div class="form-group">
										<label class="control-label"> Gender </label>
										<div>
											<c:choose>
												<c:when test="${user.gender.equalsIgnoreCase('Male')}">
													<label class="radio-inline"> <input type="radio"
														class="grey" value="Female" name="gender" id="gender_female">
														Female
													</label>
													<label class="radio-inline"> <input type="radio"
														class="grey" value="Male" name="gender" id="gender_male"
														checked="checked"> Male
													</label>
												</c:when>
												<c:otherwise>
													<label class="radio-inline"> <input type="radio"
														class="grey" value="Female" name="gender" id="gender_female"
														checked="checked"> Female
													</label>
													<label class="radio-inline"> <input type="radio"
														class="grey" value="Male" name="gender" id="gender_male">
														Male
													</label>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label"> Position </label>
										<form:input class="form-control" id="position" path="position"
										disabled="true" />
									</div>
									<div class="form-group connected-group">
										<label class="control-label"> Date of Birth </label>
										<div class="row">
											<div class="col-md-3">
												<form:select path="day" id="dd" class="form-control">
													<form:option value="">DD</form:option>
													<form:option value="01">1</form:option>
													<form:option value="02">2</form:option>
													<form:option value="03">3</form:option>
													<form:option value="04">4</form:option>
													<form:option value="05">5</form:option>
													<form:option value="06">6</form:option>
													<form:option value="07">7</form:option>
													<form:option value="08">8</form:option>
													<form:option value="09">9</form:option>
													<form:option value="10">10</form:option>
													<form:option value="11">11</form:option>
													<form:option value="12">12</form:option>
													<form:option value="13">13</form:option>
													<form:option value="14">14</form:option>
													<form:option value="15">15</form:option>
													<form:option value="16">16</form:option>
													<form:option value="17">17</form:option>
													<form:option value="18">18</form:option>
													<form:option value="19">19</form:option>
													<form:option value="20">20</form:option>
													<form:option value="21">21</form:option>
													<form:option value="22">22</form:option>
													<form:option value="23">23</form:option>
													<form:option value="24">24</form:option>
													<form:option value="25">25</form:option>
													<form:option value="26">26</form:option>
													<form:option value="27">27</form:option>
													<form:option value="28">28</form:option>
													<form:option value="29">29</form:option>
													<form:option value="30">30</form:option>
													<form:option value="31">31</form:option>
												</form:select>
											</div>
											<div class="col-md-3">
												<form:select path="month" id="mm" class="form-control">
													<form:option value="">MM</form:option>
													<form:option value="01">1</form:option>
													<form:option value="02">2</form:option>
													<form:option value="03">3</form:option>
													<form:option value="04">4</form:option>
													<form:option value="05">5</form:option>
													<form:option value="06">6</form:option>
													<form:option value="07">7</form:option>
													<form:option value="08">8</form:option>
													<form:option value="09">9</form:option>
													<form:option value="10">10</form:option>
													<form:option value="11">11</form:option>
													<form:option value="12">12</form:option>
												</form:select>
											</div>
											<div class="col-md-3">
												<form:input type="text" id="yyyy" path="year"
													class="form-control" />
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label"> Email Id </label>
										<form:input class="form-control" id="emailId"
											path="emailId" />
									</div>
									<div class="form-group">
										<label class="control-label"> Contact No </label>
										<form:input class="form-control" id="contactNo"
											path="contactNo" />
									</div>
									<div class="form-group">
										<label class="control-label"> Address </label>
										<form:input class="form-control" id="address"
											path="address" />
									</div>
									<div class="form-group">
										<label class="control-label"> City </label>
										<form:input class="form-control" id="city"
											path="city" />
									</div>
									<div class="form-group">
										<label class="control-label"> State </label>
										<form:input class="form-control" id="state"
											path="state" />
									</div>
									<div class="form-group">
										<label class="control-label"> Zipcode </label>
										<form:input class="form-control" id="zipcode"
											path="zipcode" />
									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-md-12">
									<div>
										Required Fields*
										<hr>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-8">
									<p>By clicking UPDATE, you are agreeing to the Policy and
										Terms &amp; Conditions.</p>
								</div>
								<div class="col-md-4">
									<button class="btn btn-teal btn-block" type="submit">
										Update <i class="fa fa-arrow-circle-right"></i>
									</button>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>