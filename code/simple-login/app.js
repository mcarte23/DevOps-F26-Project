//Setup sqlite to manage db connection/query
const sqlite3 = require('sqlite3').verbose();

//Define basic SQL query to fetch all TODO items
const fetchUserQuery = 'SELECT * FROM USERS WHERE Username = ?';
const insertUserQuery = 'INSERT INTO USERS (Username, Password) VALUES (?, ?)';

//Define function to connect to DB
const connectToDB = function() {
	return new sqlite3.Database('./user-store.db', sqlite3.OPEN_READWRITE, 
									(err) => {
									  if (err){
										  console.error('Connection error:', err.message);
										  process.exit(1)
									  }
									  console.log('Connected to the SQLite database.');
									}
								);
}

//Define function to close DB
const closeDB = function(db) {
	db.close((err) => {
	  if (err) {
		  return console.error('Closure error:', err.message);
	  }
	  console.log('Database connection closed.');
	});
}

//Define function to insert into DB - promise supports synchronous resolution
const insertUser = async (db, sql, params) => {
	return new Promise((resolve, reject) => {
		db.run(sql, params, function(err) {
			if (err) {
			  reject(err);
			}
			console.log('Inserted a user with the following information.');
			console.log('Username:', params[0]);
			console.log('Password:', params[1]);
            resolve();
		});
    });
};

const fetchUser = async (db, sql, params) => {
	return new Promise((resolve, reject) => {
		db.get(sql, params, (err, row) => {
			if (err) {
				reject(err)
			}
            if (row) {
                console.log("Found user: ", row.Username);
            } else {
                console.log("No user found with username: ", params[0]);
            }
			resolve(row);
		})
	})
}

//setup express to handle routing and rendering
var express = require('express');
var app = express();
app.set('view engine', 'ejs');

//setup bodyparser to extra data from form posts
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true })); 

//setup static public directory (to link images from view pages)
app.use(express.static('public'))

// route to index page
app.get('/', async function(req, res) {
	res.render('page/login', {error: null})
});

app.get('/landing', async function(req, res) {
    var username = req.query.username;
    if (!username) {
        return res.redirect("/");
    } 
    res.render('page/landing', {name: username});
})

app.get('/create_account', async function(req, res) {
	res.render('page/create_account', {error: null});
});

app.post('/api/login', async function(req, res) {
	var username = req.body.username
	var password = req.body.password;
	
	const db = new connectToDB();

	try {
		const user = await fetchUser(db, fetchUserQuery, [username]);
        if (user && user.Password == password) {
            res.redirect('/landing?username=' + encodeURIComponent(user.Username));
        } else {
            res.render('page/login', {error: 'Invalid username or password'});
        }
	} catch (err) {
        console.log(err);
        res.render('page/login', {error: 'Server error.'});
    } finally {
        closeDB(db);
    }
})

app.post('/api/add_user', async function(req, res) {
	//setup params for query from form elements
	var username = req.body.username;
	var password = req.body.password;

    if (!username || !password) {
        return res.render('page/create_account', {error: "Username and Password are required"});
    }
	const params = [username, password]
	
	const db = new connectToDB();
	
	try {
		const user = await fetchUser(db, fetchUserQuery, [username]);
        if (user) {
            res.render('page/create_account', {error: "Username already exists."});
        } else {
            await insertUser(db, insertUserQuery, params);
            res.redirect('/landing?username=' + encodeURIComponent(username));
       } 
	} catch (err) {
		console.log(err);
		res.render('page/create_account', {error: "Server error"});
	} finally {
		closeDB(db);
	}
});

//start the web app
app.listen(8080, function () {
	console.log('App listening on port 8080!');
});

