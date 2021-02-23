const express = require('express');
const dotenv = require('dotenv');
const morgan = require('morgan');
const colors = require('colors');
const connectDB = require('./config/db');
const cors = require('cors');
const path = require('path');

// Load env variables
dotenv.config({ path: `./config/config.env` });

// Connect to mongoDB
connectDB();

const app = express();

//Body parser
app.use(express.json());

app.get('/', (req, res) => {
    res.send('Hello from express');
});

//Development env logging middleware
if (process.env.NODE_ENV === 'development') {
    app.use(morgan('dev'));
}


//Enable Cors
app.use(cors());

//Set static folder
app.use(express.static(path.join(__dirname, 'public')));

const PORT = process.env.PORT || 5000;
const server = app.listen(
    PORT,
    console.log(
        `Travel Group Server running in ${process.env.NODE_ENV} mode on port ${PORT}`.yellow.bold
    )
);

// Handle unhandled promise rejections
process.on('unhandledRejection', (err, promise) => {
  console.log(`Error: ${err.message}`.red);
  // Close server & exit process
  // server.close(() => process.exit(1));
});


