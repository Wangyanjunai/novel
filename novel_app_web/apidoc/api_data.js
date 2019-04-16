define({ "api": [
  {
    "type": "GET",
    "url": "/loading/getData",
    "title": "加载加载广告图片",
    "version": "0.0.1",
    "group": "Users",
    "description": "<p>急速追书小说APP初始页面加载广告图片接口</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "OrderForm",
            "optional": false,
            "field": "orderForm",
            "description": "<p>订单form</p>"
          },
          {
            "group": "Parameter",
            "type": "BindingResult",
            "optional": false,
            "field": "bindingResult",
            "description": "<p>订单验证信息</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求样例：",
          "content": "?account=sodlinken&password=11223344&mobile=13739554137&vip=0&recommend=",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "code",
            "description": "<p>0 代表无错误 1代表有错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"0\",\"msg\":\"注册成功\"}",
          "type": "json"
        }
      ]
    },
    "filename": "novel_app_web/src/main/java/com/potato369/novel/app/web/controller/LoadingController.java",
    "groupTitle": "Users",
    "name": "GetLoadingGetdata"
  }
] });
