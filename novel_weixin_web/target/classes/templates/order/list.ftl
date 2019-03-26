<html>
    <#include "../common/header.ftl">
    <body>
        <div id="wrapper" class="toggled">
            <#--边栏 sidebar-->
            <#include "../common/nav.ftl">
            <#--主体内容区域 content-->
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row clearfix">
                        <!--表格数据-->
                        <div class="col-md-12 column">
                            <table class="table table-bordered table-hover table-condensed">
                                <thead>
                                <tr>
                                    <th style="text-align: center">订单id</th>
                                    <th style="text-align: center">订单名称</th>
                                    <th style="text-align: center">买家姓名</th>
                                    <th style="text-align: center">微信openid</th>
                                    <th style="text-align: center">地址</th>
                                    <th style="text-align: center">金额</th>
                                    <th style="text-align: center">订单状态</th>
                                    <th style="text-align: center">支付状态</th>
                                    <th style="text-align: center">创建时间</th>
                                    <th style="text-align: center">修改时间</th>
                                    <th colspan="2" style="text-align: center">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <#list orderDTOPage.content as orderDTO>
                                        <tr class="success">
                                            <td>${orderDTO.orderId}</td>
                                            <td>${orderDTO.orderName}</td>
                                            <td>${orderDTO.buyerName}</td>
                                            <td>${orderDTO.buyerOpenid}</td>
                                            <td>${orderDTO.buyerAddress}</td>
                                            <td>${orderDTO.orderAmount}</td>
                                            <td>${orderDTO.getOrderStatusEnum().message}</td>
                                            <td>${orderDTO.getPayStatusEnum().message}</td>
                                            <td>${orderDTO.createTime}</td>
                                            <td>${orderDTO.updateTime}</td>
                                            <td><a href="/novel/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                                            <td>
                                                <#if orderDTO.getOrderStatusEnum().code == 0>
                                                    <a href="/novel/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                                                </#if>
                                            </td>
                                        </tr>
                                    </#list>
                                </tbody>
                            </table>
                        </div>
                        <!--分页-->
                        <div class="col-md-12 column">
                            <ul class="pagination pull-right">
		                        <#if currentPage lte 1>
		                            <li class="disabled"><a href="#">上一页</a></li>
		                        <#else>
		                            <li><a href="/novel/order/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
		                        </#if>
			                    <#if orderDTOPage.totalPages gt 0>
                                    <#list 1..orderDTOPage.totalPages as index>
                                        <#if currentPage == index>
			                            <li class="disabled"><a href="#">${index}</a></li>
                                        <#else>
			                            <li><a href="/novel/order/list?page=${index}&size=${size}">${index}</a></li>
                                        </#if>
                                    </#list>
                                <#else>
                                    <li class="disabled"><a href="#">${currentPage}</a></li>
			                    </#if>
		                        <#if currentPage gte orderDTOPage.totalPages>
		                            <li class="disabled"><a href="#">下一页</a></li>
		                        <#else>
		                            <li><a href="/novel/order/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
		                        </#if>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <#--弹窗-->
        <div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button onclick="closePopWindow();" type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title" id="myModalLabel">【温馨提醒】</h4>
                    </div>
                    <div class="modal-body">您有新的楼兰书城书币购买订单，订单号：<span id="message"></span>，请点击“查看新的订单”按钮，查看新的订单信息！！！</div>
                    <div class="modal-footer">
                        <button onclick="closePopWindow();" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button onclick="seeNewOrder();" type="button" class="btn btn-primary">查看新的订单</button>
                    </div>
                </div>
            </div>
        </div>

        <#--播放音乐-->
        <audio id="notice" loop="loop"><source src="/novel/mp3/song.mp3" type="audio/mpeg" /></audio>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>

        <script>
            var webSocket = null;
            if ("WebSocket" in window) {
                //开发环境（dev） ：
                webSocket = new WebSocket("wss://potato369.mynatapp.cc/novel/webSocket");
                //测试环境（test）：webSocket = new WebSocket("wss://potato369.mynatapp.cc/novel/webSocket");
                //生产环境（prod）：webSocket = new WebSocket("wss://www.potato369.com/novel/webSocket");
            } else {
                alert("浏览器不支持WebSocket！！！");
            }
            webSocket.onopen = function (event) {
                console.log("与服务器端WebSocket建立连接！！！", event.data);
            }
            webSocket.onclose = function (event) {
                console.log("服务器端WebSocket关闭连接！！！", event.data);
            }
            webSocket.onmessage = function (event) {
                console.log("收到服务器端WebSocket广播消息：", event.data);
                // 弹窗提醒，同时播放音乐
                $("#message").html('<label style="color: red; font-weight: bold;">'+event.data+'</label>');
                $('#myModal').modal('show');
                document.getElementById("notice").play();
            }
            webSocket.onerror = function () {
                console.log("与服务器端WebSocket通讯发生错误！！！");
            }
            window.onbeforeunload = function (event) {
                console.log("离开（刷新或关闭）当前页面，关闭与服务器端WebSocket连接！！！", event.data);
                webSocket.close();
            }
            function closePopWindow() {
            	document.getElementById('notice').pause();
            	window.location.reload();
            }
            function seeNewOrder() {
                var orderId = $("#message").text().trim();
                document.getElementById('notice').pause();
                window.location.href='/novel/order/detail?orderId='+ orderId;
            }
        </script>
    </body>
</html>
