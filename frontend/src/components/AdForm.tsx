import { useState } from "react";
import "./AdForm.css";
import {useNavigate} from "react-router-dom";

export default function AdForm() {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [sportTypeId, setSportTypeId] = useState("");
  const [location, setLocation] = useState("");
  const [participants, setParticipants] = useState<number>(1);

  const [dateStart, setDateStart] = useState("");
  const [dateEnd, setDateEnd] = useState("");
  const [timeStart, setTimeStart] = useState("");
  const [timeEnd, setTimeEnd] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    const adData = {
      title,
      description,
      sportTypeId: Number(sportTypeId),
      dateStart,
      dateEnd,
      timeStart,
      timeEnd,
      location,
      participants,
    };

    console.log("Submitting ad:", adData);
    // todo send submitted ad to POST /api/ads

    // backend
    // po sukcesie:
    // navigate("/ads");

    try {
      const response = await fetch(`${import.meta.env.VITE_API_URL}/api/ads`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${localStorage.getItem("accessToken")}`
        },
        body: JSON.stringify(adData),
      });

      if (response.ok) {
        // TODO
      } else {
        const errorText = await response.text();
        console.error("Login failed: " + errorText);
      }
    } catch (error) {
      console.error("Unable to connect to the server.");
      if (error instanceof Error) {
        console.error("An error occured while logging in:\n" + error);
      }
    }

  };

  return (
    <div className="form-container">
      <h2>Create New Sport Ad</h2>
      <form onSubmit={handleSubmit} className="ad-form">
        <label htmlFor="ad-title">Ad title</label>
        <input
          type="text"
          id="ad-title"
          placeholder="Ad title"
          value={title}
          required
          onChange={(event) => setTitle(event.target.value)}
        />

        <label htmlFor="description">Description (optional)</label>
        <textarea
          placeholder="Enter description here..."
          id="description"
          value={description}
          onChange={(event) => setDescription(event.target.value)}
        />

        <label htmlFor="sport-type">Sport type:</label>
        <select id="sport-type" value={sportTypeId} required onChange={(event) => setSportTypeId(event.target.value)}>
          <option value="">Select sport</option>
          <option value="1">Football</option>
          <option value="2">Basketball</option>
          <option value="3">Tennis</option>
          {/* backend */}
        </select>

      <label htmlFor="location">Location:</label>
        <input
          type="text"
          id="location"
          placeholder="Location"
          value={location}
          required
          onChange={(event) => setLocation(event.target.value)}
        />


        <label htmlFor="number-of-participants">Number of participants</label>
        <input
          type="number"
          id="number-of-participants"
          value={participants}
          min={1}
          required
          onChange={(event) => setParticipants(parseInt(event.target.value))}
        />

        <label>Start Date:</label>
        <input type="date" value={dateStart} required onChange={(event) => setDateStart(event.target.value)} />

        <label>End Date:</label>
        <input type="date" value={dateEnd} required onChange={(event) => setDateEnd(event.target.value)} />

        <label>Start Time:</label>
        <input type="time" value={timeStart} required onChange={(event) => setTimeStart(event.target.value)} />

        <label>End Time:</label>
        <input type="time" value={timeEnd} required onChange={(event) => setTimeEnd(event.target.value)} />

        <button type="submit">Publish</button>
      </form>
    </div>
    // trzeba dorobić plik css do tego
  );
}
