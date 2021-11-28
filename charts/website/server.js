const http = require('http');
const express = require('express');
const mysql = require('mysql');

const path = require('path');
const app = express();



app.use(express.json());
app.use(express.static("express"));
app.set('view engine', 'ejs');
// default URL for website


const db = mysql.createConnection({
    host: "127.0.0.1",
    user: "root",
    password: "jlL()QUEBP@sGgTNEETj",
    database: "proyecto"
}); 

app.get('/', function(req,res){
    res.render(path.join(__dirname + '/express/homepage.ejs'));
  });


app.get('/largo', function(req,res){
    console.log("hola!")
    const sql = 'select release_date, avg(len) as largo from lyrics group by release_date'
    db.query(sql, (err, result) => {
        if (err) {
            console.log(err);
            return (err);
            
        }
        if (!result || !result.length) {
            console.log("Sin respuesta")
            return next(new Error('no results'))
        }
        console.log("todo ok")
        const lyrics = result;
        res.render(path.join(__dirname + '/express/largo_por_anno.ejs'), { lyrics: lyrics } );
    })


  });
const server = http.createServer(app);
const port = 3000;
server.listen(port);
console.debug('Server listening on port ' + port);