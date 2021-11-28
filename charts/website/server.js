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
    const sql = 'select release_date, avg(len) as largo from lyrics where release_date != 0 group by release_date'
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

app.get('/sentimientos-anno', function(req,res){
    console.log("hola!")
    const sql = 'select release_date, avg(points) as sentimientos from lyrics where release_date != 0 group by release_date'
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
        res.render(path.join(__dirname + '/express/sentimientos_anno.ejs'), { lyrics: lyrics } );
    })


});

app.get('/sentimientos-genero', function(req,res){
    console.log("hola!")
    const sql = "select genre, avg(points) as sentimientos from lyrics where genre != 'genre' group by genre"
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
        res.render(path.join(__dirname + '/express/sentimientos_genero.ejs'), { lyrics: lyrics } );
    })


});

app.get('/sentimientos-genero-anno', function(req,res){
    console.log("hola!")
    const sql = "select release_date, genre, avg(points) as sentimientos from lyrics where genre != 'genre' group by release_date,genre order by release_date "
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
        res.render(path.join(__dirname + '/express/sentimientos_genero_anno.ejs'), { lyrics: lyrics } );
    })


});


app.get('/ratio-anno', function(req,res){
    console.log("hola!")
    const sql = 'select release_date, sum(case when points >0 then 1 else 0 end)/sum(case when points < 0 then 1 else 0 end) as ratio from lyrics where release_date != 0 group by release_date order by release_date'
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
        res.render(path.join(__dirname + '/express/ratio_anno.ejs'), { lyrics: lyrics } );
    })


});

app.get('/theme-anno', function(req,res){
    console.log("hola!")
    const sql = `
    SELECT release_date, theme, apariciones FROM (
        SELECT release_date ,
                theme,
                COUNT(theme) AS apariciones
            FROM lyrics
            WHERE release_date != 0
        GROUP BY  release_date, theme
        ORDER BY release_date, apariciones DESC)
    GROUP BY  release_date
    ORDER BY release_date
    `
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
        res.render(path.join(__dirname + '/express/theme_anno.ejs'), { lyrics: lyrics } );
    })


});

const server = http.createServer(app);
const port = 3000;
server.listen(port);
console.debug('Server listening on port ' + port);