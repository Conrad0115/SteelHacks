import { AppBar, Button, IconButton } from "@mui/material";

import AddIcon from "@mui/icons-material/Add";
import DeleteIcon from "@mui/icons-material/Delete";
import RemoveIcon from "@mui/icons-material/Remove";
import Divider from "@mui/material/Divider";
import Input from "@mui/material/Input";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import React, { useEffect, useRef, useState } from "react";
import { createWorker } from "tesseract.js";
import "./App.css";

function App() {
  const fileInputRef = useRef(null);
  const [recommendation, setRecommendation] = useState([]);
  
  useEffect(() => {
    fetch(process.env.REACT_APP_API_URL + "/api/recommendation")
      .then((response) => {
        console.log(response);
       return  response.json();
      })
      .then((data) => {
        console.log(data)
        setRecommendation(data);
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  }, []);


  // receipt
  const handleClick = () => {
    fileInputRef.current.click();
  };

  const handleFileUpload = (event) => {
    const file = event.target.files[0];

    (async () => {
      const worker = await createWorker("eng");
      const ret = await worker.recognize(file);
      const lines = ret.data.text.split("\n");
      // Map each line to an object containing the name and price
      const foodItems = lines.map((line) => {
        // Define a regular expression pattern to match the last whitespace character
        const pattern = /\s+(?=\S*$)/;

        // Split the line into name and price based on the pattern
        const parts = line.split(pattern);

        // Extract the name and price
        const name = parts[0].trim(); // Extract the name and remove leading/trailing spaces
        const price = (parts[1]||"  ").substring(1);

        // Return an object containing the name and price
        return { name: name||"", price: price ||""};
      });

      console.log(JSON.stringify(foodItems));
      console.log(process.env.REACT_APP_API_URL)
      fetch(process.env.REACT_APP_API_URL + "/api/upload", {
        method: "POST",
        body: JSON.stringify(foodItems),
      })
        .then((response) => {
        
          return response.json();
        })
        .then((data) => {
          console.log("Success:", data);
        })
        .catch((error) => {
          console.error("Error:", error);
        });
      await worker.terminate();
    })();
    // Do something with the uploaded file (e.g., send to server)
  };

  // Current list
  const [shoppingList, setShoppingList] = useState([ ]);
  useEffect(() => {
    fetch(process.env.REACT_APP_API_URL + "/api/getList")
      .then((response) => {
        console.log(response);
       return  response.json();
      })
      .then((data) => {
        console.log(data)
        setShoppingList(data);
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  }, []);

  const handleDecrement = (index) => {
    const newList = [...shoppingList];
    newList[index].count = Math.max(1, newList[index].count - 1); // Prevent going below 1
    setShoppingList(newList);
  };
  const handleIncrement = (index) => {
    const newList = [...shoppingList];
    newList[index].count += 1;
    setShoppingList(newList);
  };
  const handleDelete = (index) => {
    const newList = shoppingList.filter((_, i) => i !== index);
    setShoppingList(newList);
  };
  const handleAddNewItem = (name) => {
    if (!name.trim()) return;
    const newItem = { count: 1, name };
    setShoppingList([...shoppingList, newItem]);
    // clear the input field
    document.getElementById("newItem").value = "";
  };

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        height: "100vh",
        width: "100vw",
      }}
    >
      <header>
        <AppBar
          sx={{
            position: "sticky",
            display: "flex",
            flexDirection: "row",
            alignItems: "center",
            justifyContent: "space-between",
            padding: "10px",
          }}
        >
          <h1 >Smart Grocery List</h1>
          <Input
            type="file"
            inputProps={{ accept: "image/*, .pdf, video/*" }}
            style={{ display: "none" }}
            inputRef={fileInputRef}
            onChange={handleFileUpload}
          />

          <Button variant="contained" onClick={handleClick}>
            Upload File
          </Button>
        </AppBar>
      </header>
      <div
        style={{
          height: "100%",
          display: "flex",
          flexDirection: "row",
          justifyContent: "space-around",
        }}
      >
        <div>
          <h2>Recommendation</h2>
          <List>
            {/*filter out that already in shopping cart*/}
            {recommendation
              .filter(
                (item) =>
                  !shoppingList.some((shoppingItem) => shoppingItem.name === item)
              )
              .map((item, index) => (
                <ListItem key={index}>{item}
                  <Button style={{marginLeft: 20}} variant="contained" size="small"  onClick={()=> handleAddNewItem(item)}>Add To List</Button>
                </ListItem>
              ))}
          </List>
        </div>

        <Divider variant="middle" orientation="vertical" flexItem />
        <div
          style={{
            display: "flex",
            flexDirection: "column",
            alignItems: "space-between",
          }}
        >
          <h2>Current List</h2>
          <List>
            <ListItem>
              <Input
                variant="outlined"
                type="text"
                id="newItem"
                name="newItem"
              />
              <IconButton
                onClick={() =>
                  handleAddNewItem(document.getElementById("newItem").value)
                }
              >
                <AddIcon />
              </IconButton>
            </ListItem>
            {shoppingList.map((item, index) => (
              <ListItem key={index}>
                {item.name}
                <div
                  style={{
                    marginLeft: "auto",
                  }}
                >
                  {item.count > 1 ? (
                    <IconButton onClick={() => handleDecrement(index)}>
                      <RemoveIcon />
                    </IconButton>
                  ) : (
                    <IconButton onClick={() => handleDelete(index)}>
                      <DeleteIcon />
                    </IconButton>
                  )}
                  {item.count}
                  <IconButton onClick={() => handleIncrement(index)}>
                    <AddIcon />
                  </IconButton>
                </div>
              </ListItem>
            ))}
          </List>
          <Button onClick={() => setShoppingList([])}>Clear List</Button>
        </div>
      </div>
    </div>
  );
}

export default App;
