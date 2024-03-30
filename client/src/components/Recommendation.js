import React, { useEffect, useState } from "react";
export default function Recommendation() {
  const [recommendation, setRecommendation] = useState([]);

  useEffect(() => {
    fetch(process.env.REACT_APP_API_URL + "/api/recommendation")
      .then((response) => response.json())
      .then((data) => setRecommendation(data));
  }, []);

  return (
    <div>
      <h2>Recommendation</h2>
      <ul>
        {recommendation.map((item) => (
          <li key={item.id}>{item.name}</li>
        ))}
      </ul>
    </div>
  );
}
