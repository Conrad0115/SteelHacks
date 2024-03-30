const express = require('express');
const app = express();

app.get('/', (req, res) => {
  res.send('Hello World!');
});

app.get('/api/shopping-list', (req, res) => {
  res.json([
    {name: 'Milk', count: 3},
    {name: 'Sugar', count: 1},
    {name: 'Tea', count: 2},
    {name: 'Eggs', count: 12},
    {name: 'Bread', count: 2}

  ]);
});

app.get('/api/recommendation', (req, res) => {

  res.json([
    {name: 'Milk'},
    {name: 'Sugar',},
    {name: 'Tea',},
    {name: 'Eggs'},
    {name: 'Oranges'}
  ]);
});

app.post('/api/upload', (req, res) => {

});


app.listen(process.env.port || 4000, () => {
  console.log(`Server is running on port ${process.env.port}`);
});
