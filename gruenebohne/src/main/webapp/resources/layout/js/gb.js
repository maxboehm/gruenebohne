      (function(anchors, url, i, a) {
    	  while ((a = anchors[i++]) && a.classList)
    	    a.href === url && a.classList.add('active');
    	}(document.getElementsByTagName('a'), location.href, 0));