(function ($, $m) {
	var _data = {}
	var modal = $('<div id="debugModal"><div></div></div>').
		appendTo('body').
		css({
			position: 'fixed',
			zIndex: 9999999,
			right: 0, top: 0,
			background: '#eee',
			border: "2px solid #666",
			borderRadius: '5px',
			color: '#000',
			padding: '10px',
			margin: '10px'
		}).children();


	var markup = '' +
			'<a href="http://isis.mtvnservices.com/Isis.html#module=content&site=ema-intl-authoring-uk&id=<%=item.uuid%>" ' +
			'target="isis">' +
			'<p><%=method%></p>' +
			'<h2><%=item.contentType%> <%=item.id%> </h2>' +
			'</a>' +
			'<b><%=item.title%></b><br/>' +
			'<%=item.uuid%><br/>' +
			'<br/>' +
			'<%=inner%>',
		mainTmpl = _.template(markup, null),
		subTpml = function (data) {
			var contentTypeTmpl = {
				'Standard:Video': _.template('This is a video', null),
				'Site:Video': _.template('This is a video', null),
			}

			subTpml = function (data) {
				if (typeof(contentTypeTmpl[data.contentType]) !== 'undefined') {
					return contentTypeTmpl[data.contentType](data)
				}
				return ''
			}

			return subTpml(data);
		}


	$(function () {
		console.log('loading')
		if (typeof window.debugItems !== 'undefined') {
			_data = window.debugItems;
		}
		console.log(_data);
		$('[data-debug-id]').
			children().
//			css({boxShadow: 'red 0 0 0 1px'}).
//            hover(_render, _hide).
			each(function () {
				var t = $(this), c = t.children();

				t.css({
//                    display: c.css('display'),
//                    boxShadow: '2px 2px 2px 2px red',
//                    width: c.width(),
//                    height: c.height()
				});
			});
	});


	var mouseX = 0;
	var mouseY = 0;
	var front = 0;
	var back = 0;

	function log(text) {
		$("#log").append(text + '<BR>');
	}

	function mouseWithin(selector) {
		var pos = $(selector).offset();
		var top = pos.top;
		var left = pos.left;
		var height = $(selector).height();
		var width = $(selector).width();

//        console.log(width,height)
//
//        console.log(mouseX >= left, mouseY >= top, mouseX <= left + width, mouseY <= top + height)
		if (mouseX >= left && mouseY >= top && mouseX <= left + width && mouseY <= top + height) {
			return true;
		}
		return false;
	}

	var dataId = $('[data-debug-id]').children();

	var _shown = $();

	function _show(f) {

		_hide();
		modal.show().html('');
		_shown = f;
		f.each(function () {
			var el = $(this),
				t = el.parent(),
				id = t.data('debug-id'),
				data = _data[id]

			console.log(t, data)

			el.css({boxShadow: 'blue 0 0 0 1px'});

			modal.parent().show();
			modal.append(mainTmpl({ item: data, inner: subTpml(data), method: t.data('method') }))
		})
	}

	function _hide() {
		modal.hide();
		_shown.each(function () {
			this.style.boxShadow = null;
		})
	}

	$(document).on('mousemove', $.debounce(1500, function (e) {
		mouseX = e.pageX;
		mouseY = e.pageY;
//        console.log('mouse', dataId)
		var f = dataId.filter(function () {
			return mouseWithin(this);
		});
		if (f.length) {
			_show(f);
		}
		else {
			_hide();
		}
	}));


}(jQuery, MTVN));