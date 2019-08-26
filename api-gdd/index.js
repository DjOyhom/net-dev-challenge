var express = require('express');
var app = express();

app.get('/:id', function (req, res) {
  var dis;

  if (req.params.id > 5) {
    var d = new Date();
    dis = d.getMinutes().toString();
  }else {
    dis = "0";
  }
  res.send(dis);
  console.log(dis + "-----------" + req.params.id);
});

app.listen(3000, function () {
  console.log('Example app listening on port 3000!');
});
