/**
 * Created by chenml on 2016/7/21.
 * js文件
 */
jQuery(document).ready(function ($) {
    $(function () {
        $("#city").submit(function () {
            return false;
        });
        var input = $('#cityName');
        //input.blur(function () {$("#search_suggest").hide();});//失去焦点隐藏//移动端不友好
        $(document).bind('click', function () {
            $("#search_suggest").hide();
        });
        input.bind("keyup", function (e) {
            var text = $(this).val();
            if (text === "") {
                //没有输入字符
                $("#search_suggest").hide();
                return false;
            }
            var key = e.which;
            var id = $('#city-id');
            if (key == 13) {//enter
                if (id.val()) {
                    window.location.href = 'weather.html?id=' + id.val();
                }
                return false;
            }
            if (key == 40 || key == 38 || key == 37 || key == 39) {// 40-down 38-up 37-left 39-right
                var wrap = $('#search_result');
                var current = wrap.find('li.hover');
                if (key == 38) {
                    if (current.length > 0) {
                        var prev = current.removeClass('hover').prev();
                        if (prev.length > 0) {
                            prev.addClass('hover');
                            current = prev;
                            $(this).val(prev.data('text'));
                        }
                    } else {
                        var last = wrap.find('li:last');
                        last.addClass('hover');
                        current = last;
                        $(this).val(last.data('text'));
                    }
                } else if (key == 40) {
                    if (current.length > 0) {
                        var next = current.removeClass('hover').next();
                        if (next.length > 0) {
                            next.addClass('hover');
                            current = next;
                            $(this).val(next.data('text'));
                        }
                    } else {
                        var first = wrap.find('li:first');
                        first.addClass('hover');
                        current = first;
                        $(this).val(first.data('text'));
                    }
                }
                id.val(current.data('id'));
                //如何通过 JQuery 将 DIV 的滚动条滚动到指定的位置
                //http://www.cnblogs.com/jaxu/archive/2013/05/17/3083019.html
                wrap.animate({scrollTop: current.offset().top - wrap.offset().top + wrap.scrollTop() - 70}, 'fast');
                //wrap.scrollTop(current.offset().top - wrap.offset().top + wrap.scrollTop() - 70);
                return false;
            }//up down

            //加入字符检查
            if (!text.match(/^[\u4e00-\u9fa5|a-zA-Z]*$/)) {
                $("#search_suggest").hide();
                return false;
            } else {
                //calc input char length
                var countArray = chEnWordCount(text);
                if (countArray.ch > 0 || countArray.en > 2) {
                } else {
                    return false;
                }
            }
            var action = $("#city").attr("action");
            $.get(action + '?cityName=' + text, function (suggestArray) {
                var html = "";
                for (var i = 0; i < suggestArray.length; i++) {
                    var suggestText = suggestArray[i].cityName;
                    var linkHerf = suggestArray[i].cityId;
                    var parent = suggestArray[i].stationName;
                    html += "<li data-text='" + suggestText + "' data-id='" + linkHerf + "'>" +
                        "<a href=weather.html?id=" + linkHerf + ">" + suggestText + "," + parent + "</a></li>";
                }
                $("#search_result").html(html);
                $("#search_suggest").show();
            }, "json");
        });
        //define function to calc character length
        function chEnWordCount(str) {
            var ch = 0, en = 0;
            if (str.match(/[\u4e00-\u9fa5]+/g)) {
                ch = str.match(/[\u4e00-\u9fa5]/g).length
            }
            if (str.match(/[a-zA-Z]+/g)) {
                en = str.match(/[a-zA-Z]/g).length
            }
            return {ch: ch, en: en}
        }
    });
});