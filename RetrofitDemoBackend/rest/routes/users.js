var express = require('express');
var router = express.Router();

/* GET users listing. */
router.get('/', function (req, res, next) {
    res.send('respond with a resource');
});

router.post('/', function (req, res, next) {
    let user = req.body;
    user.id = parseInt(Math.random() * 10000);
    res.json(user);
});

// router.post('/upload', function (req, res, next) {
//     console.log(req.body.client + " : " + req.body.secret);
//     console.log(req.body.description + " : " + req.body.photographer + " : " + req.body.year + " : " + req.body.location);
//
//     if (!req.files)
//         return res.status(400).send('No files were uploaded.');
//
//     // The name of the input field (i.e. "sampleFile") is used to retrieve the uploaded file
//     let sampleFile = req.files.photo;
//
//     // Use the mv() method to place the file somewhere on your server
//     sampleFile.mv("public/images/" + sampleFile.name, function (err) {
//         if (err)
//             return res.status(500).send(err);
//
//         res.send('File uploaded!');
//     });
// });
//
// router.post('/uploadTwoPhotos', function (req, res, next) {
//     if (!req.files)
//         return res.status(400).send('No files were uploaded.');
//
//     // The name of the input field (i.e. "sampleFile") is used to retrieve the uploaded file
//     let photo1 = req.files.photo1;
//     let photo2 = req.files.photo2;
//
//     // Use the mv() method to place the file somewhere on your server
//     photo1.mv("public/images/" + photo1.name, function (err) {
//         if (err)
//             return res.status(500).send(err);
//
//         else {
//             photo2.mv("public/images/" + photo2.name, function (err) {
//                 if (err)
//                     return res.status(500).send(err);
//             });
//         }
//
//         res.send('File uploaded!');
//     });
// });

router.post('/uploadAlbum', function (req, res, next) {

    var multiparty = require('multiparty');
    var form = new multiparty.Form();

    form.parse(req, function (err, fields, files) {

        const fs = require('fs');
        console.log(fields.description[0]);

        Object.keys(files).forEach(function (key) {
            let file = files[key][0];
            fs.readFile(file.path, function (err, data) {
                var path = './public/images/' + new Data() + file.originalFilename;
                fs.writeFileSync(path, data, function (error) {
                    if (error) res.send('Failed to Save Files!');
                });
            });
        });

        res.send('Files uploaded!');

    });

});

module.exports = router;
