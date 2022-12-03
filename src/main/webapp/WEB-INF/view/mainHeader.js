(function ($) {
  $(document).ready(function(){

	$("#mainMenu").hide();
	
	$("#mainMenuSidebar").sidebar("attach events", "#btnSidebarToggle");

	
	
	$(function () {
		$(window).scroll(function () {
			if ($(this).scrollTop() > 200) {
				$('#mainMenu').fadeIn();
			} else {
				$('#mainMenu').fadeOut();
			}
		});

	
	});

	
});
  }(jQuery));