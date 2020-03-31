<%--
  Created by IntelliJ IDEA.
  User: 48137
  Date: 2020/3/29
  Time: 21:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="../static/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="../static/jquery.form.js"></script>
</head>
<body>
        <form action="/upload" method="POST" enctype="multipart/form-data">
            <input type="file" name="file"/>
            <input type="submit" value="上传">
        </form>
        文件列表：

        <table>
            <thead>
            <th>UUID</th>
            <th>文件名</th>
            <th>操作</th>
            </thead>
            <tbody id="test">
            </tbody>
        </table>

    <script type="text/javascript">
        $(document).ready(function () {
            $.ajax({
                type: "get",
                url: "download/getFileList",
                data: {},
                dataType:"json",
                success: function (data) {
                    var html="";
                        var len=data.length;

                        for(var i=0;i<len;i++){
                            html += "<tr>" +
                                "<td>"+data[i].uuid+"</td>" +
                                "<td>"+data[i].orgName+"</td>" +
                                "<td><input type='button' value='下载' onclick='downLoadThisFile(this)'uuid='"+data[i].uuid+"'></td>" +
                                "</tr>";
                        }
                        $("#test").html(html);
                }
            });
        });

        function downLoadThisFile(obj){
            var uuid=$(obj).attr("uuid");
            console.log("下载该文件"+uuid);
            $.ajax({
                type: "post",
                url: "download/downLoadThisFile",
                data: {"uuid":uuid},
                success: function (data) {
                    if(data.code=="200"){

                    }

                }
            });
        }

    </script>
</body>



</html>
