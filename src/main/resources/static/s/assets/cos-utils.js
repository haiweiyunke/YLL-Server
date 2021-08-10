var cosObj;     //COS实例

var cosUtils = {
    /**
     * COS基础配置
     */
    config : {
        Bucket: '',
        Region: '',
        path: '',
        url: '/rest/cos-keys/',
        configUrl: '/rest/config/'
    },
    /**
     * 文件夹上传
     * @param param
     */
    uploadFolder : function(param, name){
        var vm = this;
        const file = param;
        //获取配置信息
        __.api('GET', cosUtils.config.configUrl, { }, function (result) {
            var data = result.data;
            cosUtils.config.Bucket = data.bucket;
            cosUtils.config.Region = data.region;
            cosUtils.config.path = data.path;
            // COS-PUT 上传
            cosUtils.cosPutFolder(file, name);
        });
    },
    /**
     * 封装上传
     * @param param
     */
    upload : function(param, model, type){
        var vm = this;
        const file = param.file;
        //获取配置信息
        __.api('GET', cosUtils.config.configUrl, { }, function (result) {
            var data = result.data;
            cosUtils.config.Bucket = data.bucket;
            cosUtils.config.Region = data.region;
            cosUtils.config.path = data.path;
            //获取文件名
            vm.getRandom(file, model, type);
        });
    },
    /**
     * 获取文件名
     * @param file
     * @param fileName
     */
    getRandom : function(file, model, type){
        if (file) {
            //获取随机文件名
            __.api('GET', '/rest/random', {}, function (result) {
                if (__.err(result)) {
                    vm.$message.warning(result.message);
                } else {
                    var name = result.data;
                    //文件名处理
                    var arry = file.name.split(".");
                    var suffix = arry[arry.length - 1];
                    name = name + '.' + suffix;
                    // COS-PUT 上传
                    cosUtils.cosPut(file, name, model, type);
                }
            });
        }
    },
    /**
     * COS-PUT简单上传
     * @param file
     * @param fileName
     */
    cosPut : function(file, fileName, model, type){
        //指定上传目录和文件名
        var key =  cosUtils.config.path + fileName;
        __.mask();
        //上传
        cosObj.putObject({
            Bucket: cosUtils.config.Bucket, /* 必须 */
            Region: cosUtils.config.Region,    /* 必须 */
            Key: key,              /* 必须 */
            StorageClass: 'STANDARD',   /* 非必，存储云类 */
            Body: file,     /* 上传文件对象 */
            onProgress: function (progressData) {
                console.log(JSON.stringify(progressData));
            }
        }, function (err, data) {
            //成功回调
            console.log(err || data);
            //文件地址
            var path = data.Location;
            if(path){
                //（此处返回json对象是为了配合cc.js中的方法使用）
                var res = {
                    data : 'https://'.concat(path)
                };
                file.path = path;   //el-ui移除文件时使用
                //上传成功后的回调函数
                cc.handleSuccess(res, file, null, model, type);
            }
            __.unmask();
        });
    },

    /**
     * COS-PUT简单上传-文件夹
     * @param file
     * @param fileName
     */
    cosPutFolder : function(file, fileName){
        //指定上传目录和文件名 （上传目录为excel图片存放地址）
        var key =  'yshd/prod/excel-upload/' + fileName + '/' + file.name;
        __.mask();
        //上传
        cosObj.putObject({
            Bucket: cosUtils.config.Bucket, /* 必须 */
            Region: cosUtils.config.Region,    /* 必须 */
            Key: key,              /* 必须 */
            StorageClass: 'STANDARD',   /* 非必，存储云类 */
            Body: file,     /* 上传文件对象 */
            onProgress: function (progressData) {
                console.log(JSON.stringify(progressData));
            }
        }, function (err, data) {
            //成功回调
            console.log(err || data);
            //文件地址
            var path = data.Location;
            if(path){
                //（此处返回json对象是为了配合cc.js中的方法使用）
                var res = {
                    data : 'https://'.concat(path)
                };
                file.path = path;   //el-ui移除文件时使用
            }
            __.unmask();
        });
    }

};

(function() {
    //加载顺序低于v5.js
   cosObj = new COS({
        getAuthorization: function (options, callback) {
            __.api('GET', cosUtils.config.url, { }, function (result) {
                var data = result.data;
                var credentials = data.credentials;
                if (credentials) {
                    callback({
                        TmpSecretId: credentials.tmpSecretId,
                        TmpSecretKey: credentials.tmpSecretKey,
                        XCosSecurityToken: credentials.sessionToken,
                        ExpiredTime: credentials.expiredTime
                    })
                }
            });
        }
    });

})();

