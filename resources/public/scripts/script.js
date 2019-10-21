$(function() {
	$('#toc-hide').click(function() {
		$(this).parent().parent().prev().addClass('show');
		$(this).parent().parent().addClass('hide');
		$(this).parent().parent().parent().addClass('no-bg');
		$('body').find('.main-content').addClass('display-full');
	})

	$('#toc-show').click(function() {
		$(this).parent().next().removeClass('hide');
		$(this).parent().removeClass('show')
		$(this).parent().parent().removeClass('no-bg');
		$('body').find('.main-content').removeClass('display-full');
	})

	function showTocHeaders() {
		$('.first').parent().addClass('show');
	}

	$('.first').click(function() {
		nextLink = $(this).parent().next().find('a');

		$('body').find('.toc-sidebar').find('.toc-visible').find('.toc-link').removeClass('show');
		showTocHeaders();
		while (nextLink.length != 0 && nextLink.attr('class') != 'toc first') {
			nextLink.parent().addClass('show');
			nextLink= nextLink.parent().next().find('a');
		}
	})

	showTocHeaders()
});
