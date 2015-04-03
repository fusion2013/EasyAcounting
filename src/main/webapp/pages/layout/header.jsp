
<!-- start: TOP NAVIGATION CONTAINER -->
<%@page import="com.fusion.ea.entity.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ include file="taglib.jsp"%>
<div class="container">
	<div class="navbar-header">
		<!-- start: RESPONSIVE MENU TOGGLER -->
		<button data-target=".navbar-collapse" data-toggle="collapse"
			class="navbar-toggle" type="button">
			<span class="clip-list-2"></span>
		</button>
		<!-- end: RESPONSIVE MENU TOGGLER -->
		<!-- start: LOGO -->
		<a class="navbar-brand" href="#" style="color:blue"> <%=((User)SecurityContextHolder.getContext()
					.getAuthentication().getDetails()).getCompany().getName() %>
		</a>
		<!-- end: LOGO -->
	</div>
	<div class="navbar-tools">
		<!-- start: TOP NAVIGATION MENU -->
		<ul class="nav navbar-right">
			<li>
				<c:choose>
					<c:when test="${selectedFile==null}">
						<a href="<%=application.getContextPath()%>/master/file/select">
							<i>No file selected</i>
						</a>
					</c:when>
					<c:otherwise>
						<a href="<%=application.getContextPath()%>/master/file/manage">
							${selectedFile.name}
						</a>
					</c:otherwise>
				</c:choose>
			</li>
			<!-- start: USER DROPDOWN -->
			<li class="dropdown current-user">
				<a data-toggle="dropdown"
					data-hover="dropdown" class="dropdown-toggle"
					data-close-others="true" href="#"> <img
						src="<%=application.getContextPath()%>/assets/images/avatar-1-small.jpg" class="circle-img" alt="">
						
						<span class="username"><%=((User)SecurityContextHolder.getContext()
						.getAuthentication().getDetails()).getFirstName() %> <%=((User)SecurityContextHolder.getContext()
						.getAuthentication().getDetails()).getLastName() %></span> <i
						class="clip-chevron-down"></i>
				</a>
				
				<ul class="dropdown-menu">
					<li><a href="<%=application.getContextPath()%>/user/profile"> <i
							class="clip-user-2"></i> &nbsp;My Profile
					</a></li>
					<li><a href="<%=application.getContextPath()%>/company/profile"> <i
							class="clip-user-2"></i> &nbsp;My Company Profile
					</a></li>
					<li><a href="<%=application.getContextPath()%>/logout"> <i class="clip-exit"></i>
							&nbsp;Log Out
					</a></li>
				</ul></li>
			<!-- end: USER DROPDOWN -->
			
		</ul>
		<!-- end: TOP NAVIGATION MENU -->
	</div>
</div>
<!-- end: TOP NAVIGATION CONTAINER -->