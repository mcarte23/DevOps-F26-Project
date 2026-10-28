var express = require("express");
var app = express();
app.set("view engine", "ejs");

var bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({ extended: true }));

app.get("/", function (req, res) {
  res.render("page/index");
});

app.get("/docs", function(req, res) {
  res.render("page/docs");
})

app.get("/quickstart", function(req, res) {
  res.render("page/quickstart");
})

app.get("/view", function(req, res) {
  res.render("page/view");
})

app.get("/details", function(req, res) {
  res.render("page/details");
})

app.use(express.static("public"))

app.listen(8080, function () {
  console.log("Listening on port 8080");
});
