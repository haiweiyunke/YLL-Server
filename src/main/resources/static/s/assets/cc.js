var cc = {
    imageAccept : '.jpg,.jpeg,.png',
    videoAccept : '.mp4,.avi,.rmvb,.mkv',
    excelAccept : '.xls,.xlsx',
    coursewaresAccept : '.ppt,.pptx,.xls,.xlsx,.doc,.docx,.pdf',
    appAccept : '.apk',
    uploadUrl : __.url("/rest/file/upload"),
    wangEditorUploadUrl : __.url("/rest/file/wangEditorUpload"),
    cos4Path : "",
    previewUrl : function(path) {
        return __.url("/rest/file/preview?path=") + path;
    },
    /**
     * 腾讯云COS回显
     * @param path
     * @returns {*}
     */
    previewCosUrl : function(path) {
        return path;
    },
    /**
     * 上传
     * @param file
     * @param fileList
     */
    handleRemove : function(file, fileList, model, type) {
        //删除文件时
        var filePath = file.path;
        if(!filePath){
            //cos上传模式
            filePath = file.raw.path;
        }
        var index = filePath.lastIndexOf('.');
        const suffix  = filePath.substring(index + 1, filePath.length);
        if(-1 != cc.imageAccept.indexOf(suffix)){
            //图片
            if(!!type && 'cover' == type){
                //封面
                model.cover = model.cover.replace(file.path,"").replace(/^,*|,*$/g,'');		//去除首尾逗号
            } else if(!!type && 'picture' == type){
                model.picture = model.picture.replace(file.path,"").replace(/^,*|,*$/g,'');		//去除首尾逗号
            } else if(!!type && 'image' == type){
                model.image = model.image.replace(file.path,"").replace(/^,*|,*$/g,'');		//去除首尾逗号
            }

            //第三方业务的上传
            if(!!type && 'headImg' == type){
                model. headImg = model. headImg.replace(file.path,"").replace(/^,*|,*$/g,'');		//去除首尾逗号
            } else if(!!type && 'personalPortraits' == type){
                model.personalPortraits = model.personalPortraits.replace(file.path,"").replace(/^,*|,*$/g,'');		//去除首尾逗号
            } else if(!!type && 'logo' == type){
                model.logo = model.logo.replace(file.path,"").replace(/^,*|,*$/g,'');		//去除首尾逗号
            } else if(!!type && 'corporateImage' == type){
                model.corporateImage = model.corporateImage.replace(file.path,"").replace(/^,*|,*$/g,'');		//去除首尾逗号
            } else if(!!type && 'businessLicense' == type){
                model.businessLicense = model.businessLicense.replace(file.path,"").replace(/^,*|,*$/g,'');		//去除首尾逗号
            } else if(!!type && 'productImages' == type){
                model.productImages = model.productImages.replace(file.path,"").replace(/^,*|,*$/g,'');		//去除首尾逗号
            } else if(!!type && 'productLicense' == type){
                model.productLicense = model.productLicense.replace(file.path,"").replace(/^,*|,*$/g,'');		//去除首尾逗号
            } else if(!!type && 'certificate' == type){
                model.certificate = model.certificate.replace(file.path,"").replace(/^,*|,*$/g,'');		//去除首尾逗号
            } else if(!!type && 'qualityCertificate' == type){
                model.qualityCertificate = model.qualityCertificate.replace(file.path,"").replace(/^,*|,*$/g,'');		//去除首尾逗号
            } else if(!!type && 'photos' == type){
                model.photos = model.photos.replace(file.path,"").replace(/^,*|,*$/g,'');		//去除首尾逗号
            }
        } else if(-1 != cc.videoAccept.indexOf(suffix)){
            //视频
            if(!!type && 'video' == type){
                model.video = model.video.replace(file.path,"").replace(/^,*|,*$/g,'');		//去除首尾逗号
            } else if(!!type && 'advertisement' == type){
                //第三方业务-广告
                model.advertisement = model.advertisement.replace(file.path,"").replace(/^,*|,*$/g,'');		//去除首尾逗号
            }
        } else if(-1 != cc.coursewaresAccept.indexOf(suffix)){
            //课件
            model.files = model.files.replace(file.path,"").replace(/^,*|,*$/g,'');		//去除首尾逗号
        } else if(-1 != cc.appAccept.indexOf(suffix)){
            //apk
            model.fileUrl = model.fileUrl.replace(file.path,"").replace(/^,*|,*$/g,'');		//去除首尾逗号
        }
        console.log(model);
    },
    handlePreview : function(file, model) {
        //点击已上传的文件链接时
        window.open(file.url, '_blank');
    },
    handleSuccess : function(response,file,fileList, model, type) {
        //文件上传成功时
        const arry = file.path.split('.');
        const suffix = arry[arry.length - 1];
        if(-1 != cc.imageAccept.indexOf(suffix)) {
            //图片
            if (!!type && 'cover' == type) {
                //封面
                model.cover = !!model.cover ? model.cover : '';
                model.cover += ',' + response.data;
            } else if (!!type && 'picture' == type) {
                //2个字段以上的图片字段
                model.picture = !!model.picture ? model.picture : '';
                model.picture += ',' + response.data;
            } else if (!!type && 'image' == type) {
                model.image = !!model.image ? model.image : '';
                model.image += ',' + response.data;
            } else if (!!type && 'wangEditor' == type) {
                //文本编辑器，直接返回url
                model.image =  response.data;
            }

            //第三方业务的上传
            if (!!type && 'headImg' == type) {
                //头像
                model.headImg = !!model.headImg ? model.headImg : '';
                model.headImg += ',' + response.data;
            } else if (!!type && 'personalPortraits' == type) {
                //达人形象照片
                model.personalPortraits = !!model.personalPortraits ? model.personalPortraits : '';
                model.personalPortraits += ',' + response.data;
            } else if (!!type && 'logo' == type) {
                //logo
                model.logo = !!model.logo ? model.logo : '';
                model.logo += ',' + response.data;
            } else if (!!type && 'corporateImage' == type) {
                //公司形象
                model.corporateImage = !!model.corporateImage ? model.corporateImage : '';
                model.corporateImage += ',' + response.data;
            } else if (!!type && 'businessLicense' == type) {
                //营业执照
                model.businessLicense = !!model.businessLicense ? model.businessLicense : '';
                model.businessLicense += ',' + response.data;
            } else if (!!type && 'productImages' == type) {
                //营业执照
                model.productImages = !!model.productImages ? model.productImages : '';
                model.productImages += ',' + response.data;
            } else if (!!type && 'productLicense' == type) {
                //产品相关许可证
                model.productLicense = !!model.productLicense ? model.productLicense : '';
                model.productLicense += ',' + response.data;
            } else if (!!type && 'certificate' == type) {
                //合格证
                model.certificate = !!model.certificate ? model.certificate : '';
                model.certificate += ',' + response.data;
            } else if (!!type && 'qualityCertificate' == type) {
                //质检证书
                model.qualityCertificate = !!model.qualityCertificate ? model.qualityCertificate : '';
                model.qualityCertificate += ',' + response.data;
            } else if (!!type && 'photos' == type) {
                //质检证书
                model.photos = !!model.photos ? model.photos : '';
                model.photos += ',' + response.data;
            }
        } else if(-1 != cc.videoAccept.indexOf(suffix)) {
            if (!!type && 'video' == type) {
                //视频
                model.video = !!model.video ? model.video : '';
                model.video += ',' + response.data;
            } else if (!!type && 'advertisement' == type) {
                //第三方业务-广告
                model.advertisement = !!model.advertisement ? model.advertisement : '';
                model.advertisement += ',' + response.advertisement;
            }
        } else if (-1 != cc.coursewaresAccept.indexOf(suffix)) {
            //课件
            model.files = !!model.files ? model.files : '';
            model.files += ',' + response.data;
        } else if (-1 != cc.appAccept.indexOf(suffix)) {
            //apk
            model.fileUrl = !!model.fileUrl ? model.fileUrl : '';
            model.fileUrl += ',' + response.data;
        }
        console.log(model);
    },
    /**
     * 去除尾部逗号
     * @param model
     */
    trim : function(model) {
        if(!!model.image){
            model.image = model.image.replace(/^,*|,*$/g,'');
        }
        if(!!model.video){
            model.video = model.video.replace(/^,*|,*$/g,'');
        }
        if(!!model.cover){
            model.cover= model.cover.replace(/^,*|,*$/g,'');
        }
        if(!!model.picture){
            model.picture= model.picture.replace(/^,*|,*$/g,'');
        }
        if(!!model.files){
            model.files= model.files.replace(/^,*|,*$/g,'');
        }
        if(!!model.fileUrl){
            model.fileUrl= model.fileUrl.replace(/^,*|,*$/g,'');
        }

        //第三方业务的上传
        if(!!model.headImg){
            model.headImg = model.headImg.replace(/^,*|,*$/g,'');
        }
        if(!!model.personalPortraits){
            model.personalPortraits = model.personalPortraits.replace(/^,*|,*$/g,'');
        }
        if(!!model.logo){
            model.logo = model.logo.replace(/^,*|,*$/g,'');
        }
        if(!!model.corporateImage){
            model.corporateImage = model.corporateImage.replace(/^,*|,*$/g,'');
        }
        if(!!model.businessLicense){
            model.businessLicense = model.businessLicense.replace(/^,*|,*$/g,'');
        }
        if(!!model.productImages){
            model.productImages = model.productImages.replace(/^,*|,*$/g,'');
        }
        if(!!model.advertisement){
            model.advertisement = model.advertisement.replace(/^,*|,*$/g,'');
        }
        if(!!model.productLicense){
            model.productLicense = model.productLicense.replace(/^,*|,*$/g,'');
        }
        if(!!model.certificate){
            model.certificate = model.certificate.replace(/^,*|,*$/g,'');
        }
        if(!!model.qualityCertificate){
            model.qualityCertificate = model.qualityCertificate.replace(/^,*|,*$/g,'');
        }
        if(!!model.photos){
            model.photos = model.photos.replace(/^,*|,*$/g,'');
        }

    },
    /**
     * 图片视频回显 , 配合element-upload使用
     * @param str  "," 分割的字串
     */
    display : function(str, type){
        var vm = this;
        if(!!str){
            if(!!!cc.cos4Path){
                cc.cos4Path = "/prod/"
            }
            var list = [];
            var args = str.trim().split(',');
            for (var i = 0; i < args.length; i++) {
                // var url = location.protocol + '//' + args[i];
                var url = args[i];
                if(-1 == args[i].indexOf('.cos')){  //".cos"为腾讯COS存储返回地址的标识。否则则为服务器磁盘存储
                    //磁盘存储
                    url = cc.previewUrl(args[i]);
                }
                list.push({
                    uid : i,
                    name : args[i].split(cc.cos4Path)[1],
                    path : args[i],
                    url : url,
                    type : type
                });
            }
            return list;
        }
    },
    /**
     * 获取cos配置
     */
    getCosConfig : function(){
        if(!!cc.cos4Path){
        } else{
            //获取配置信息
            __.api('GET', "/rest/msPath", { }, function (result) {
                var data = result.data;
                cc.cos4Path = data.msPath;
            });
        }
    },
    /**
     * 富文本编辑器
     * @param id  div的id
     * @param model  需要赋值的对象
     * @param content  回显内容
     */
    wangEditor: function (id, model, content, type) {
        var E = window.wangEditor;
        var editor = new E('#' + id);
        // editor.customConfig.uploadImgServer = cc.wangEditorUploadUrl;
        editor.customConfig.uploadImgServer = cc.wangEditorUploadUrl;
        editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024;
        editor.customConfig.uploadImgMaxLength = 5;
        editor.customConfig.zIndex = 100;       //默认为10000，防止层级过高遮挡其他dom元素
        editor.customConfig.pasteFilterStyle = false;       //关闭粘贴样式的过滤
        editor.customConfig.uploadFileName = 'wangEditor';
        editor.customConfig.onchange = function (html) {
            if(!!type && 'material' == type){
                //自定义-素材项
                model.material = html;
            } else{
                model.content = html;       //content 为约定表单内容属性名称
            }
        };
        editor.customConfig.uploadImgHooks = {
            before: function (xhr, editor, files) {
                // 图片上传之前触发
                // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，files 是选择的图片文件

                // 如果返回的结果是 {prevent: true, msg: 'xxxx'} 则表示用户放弃上传
                // return {
                //     prevent: true,
                //     msg: '放弃上传'
                // }

                //TODO  改成COS上传，将COS返回地址直接赋值给富文本
                /*console.log(files);
                var param = {
                    file : files[0],
                    image : ''
                };
                cosUtils.upload(param, param, 'wangEditor');
                console.log('======编辑器========');
                console.log(param);*/
            },
            customInsert: function (insertImg, result, editor) {
                // 图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片！！！）
                // insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果
                // result 必须是一个 JSON 格式字符串！！！否则报错
                // 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：
                var url = cc.previewUrl(result.data);
                insertImg(url);
            }
        };
        editor.create();
        //回显内容
        if(!!content){
            editor.txt.html(content);
        }
    },
    /**
     * 富文本编辑器公共代码添加
     * @param str  传入的文本信息
     */
    editorImportHtml : function(str){
        if(!__.isEmpty(str) &&  -1 == str.indexOf('<style>img { width: 100%; }')){
            //添加此样式，可让文字、图篇在移动端自适应
            var html = '<meta name="viewport" content="width=width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">'
            html += '<style>img { width: 100%; }</style>';
            str =  html + str;
        }
        return str;
    },
    /**
     * 截取字符串至指定字符出现的指定位置
     * @param str
     * @param regular
     * @param start
     * @returns {string|*}
     */
    lastIndexOf : function(str, regular, start){
        if(str.lastIndexOf(',') != -1){
            return str.substring(start, str.lastIndexOf(regular));
        }
        return str;
    },
    /**
     * 内容溢出格式化
     * @param value
     * @returns {string|*}
     */
    ellipsis : function(value, column, cellValue) {
        if (!value) return "";
        if (value.length > 30) {
            return value.slice(0, 30) + "...";
        }
        return value;
    },

    /**
     * 后台返回时间格式带T调整正常格式
     * @param value
     * @returns {string}
     * @constructor
     */
    Time : function(value) {
        var dateee = new Date(value).toJSON();
        var date = new Date(+new Date(dateee)+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'')
        return date;
    },
    /**
     *   金额显示.00格式
     * @param value
     * @returns {string}
     * @constructor
     */
    Time : function(value) {
        if(!value) return '0.00'
        value = value.toFixed(2)
        var intPart = Math.trunc(value)// 获取整数部分
        var intPartFormat = intPart.toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,') // 将整数部分逢三一断
        var floatPart = '.00' // 预定义小数部分
        var value2Array = value.split('.')
        // =2表示数据有小数位
        if(value2Array.length === 2) {
            floatPart = value2Array[1].toString() // 拿到小数部分
            if(floatPart.length === 1) { // 补0,实际上用不着
                return intPartFormat + '.' + floatPart + '0'
            } else {
                return intPartFormat + '.' + floatPart
            }
        } else {
            return intPartFormat + floatPart
        }
    },

};

(function() {
    //cos配置初始化
    cc.getCosConfig();
})();
