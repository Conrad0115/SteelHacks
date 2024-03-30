import {AppBar, Button} from "@mui/material";

import Divider from "@mui/material/Divider";
import Input from "@mui/material/Input";
import {useRef} from "react";
import "./App.css";
import CurrentList from "./components/CurrentList";
import Info from "./components/Info";
import Recommendation from "./components/Recommendation";
import {createWorker} from 'tesseract.js';

function App() {
  const fileInputRef = useRef(null);

  const handleClick = () => {
    fileInputRef.current.click();
  };

  const handleFileUpload = (event) => {
    const file = event.target.files[0];


    (async () => {
      const worker = await createWorker('eng');
      const ret = await worker.recognize(file);
      console.log(ret.data.text);
      fetch(
        process.env.REACT_APP_API_URL + "api/upload",
        {
          method: "POST",
          body: ret.data.text,
        }
      )
        .then((response) => {
          if (!response.ok) {
            alert("Error uploading file");
          }
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
          <h1>Title </h1>
          <Input
            type="file"
            inputProps={{accept: "image/*, .pdf, video/*"}}
            style={{display: "none"}}
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
        <Recommendation/>
        <Divider variant="middle" orientation="vertical" flexItem/>
        <CurrentList/>
        <Divider variant="middle" orientation="vertical" flexItem/>
        <Info/>
      </div>
    </div>
  );
}

export default App;
