/**
 * Created by chenml on 2016/7/21.
 * js文件
 */
jQuery(document).ready(function ($) {
    $(function () {
        $("#city").submit(function () {
            return false;
        });
        $("#cityName").bind("keyup", function () {
            var text = $(this).val();
            if (text === "") {
                //没有输入字符
                $("#search_suggest").hide();
                return false;
            }
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
                flag = suggestArray.length != 0;
                var html = "";
                for (var i = 0; i < suggestArray.length; i++) {
                    var suggestText = suggestArray[i].cityName;
                    var linkHerf = suggestArray[i].cityId;
                    var parent = suggestArray[i].stationName;
                    html += "<li><a href=weather.html?id=" + linkHerf + ">" + suggestText + "," + parent + "</a></li>";
                }
                $("#search_result").html(html);

                //    overflow-y: scroll;
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