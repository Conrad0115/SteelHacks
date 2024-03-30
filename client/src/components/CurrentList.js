import {useEffect, useState} from "react";

export default function CurrentList() {
  const [shoppingList, setShoppingList] = useState([
    {id: 1, name: "Apples"},
    {id: 2, name: "Bananas"},
    {id: 3, name: "Carrots"},
    {id: 4, name: "Dates"},
  ]);
  useEffect(() => {
    fetch(process.env.REACT_APP_API_URL + "/shopping-list")
      .then((response) => response.json())
      .then((data) => console.log(data))
  }, []);
  return (
    <div>
      <h2>Current List</h2>
      <ul>
        {shoppingList.map((item) => (
          <li key={item.id}>{item.name}</li>
        ))}
      </ul>
    </div>
  )
}