import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function AdForm() {
  const navigate = useNavigate();

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [sportTypeId, setSportTypeId] = useState("");
  const [location, setLocation] = useState("");
  const [participants, setParticipants] = useState<number>(1);

  const [dateStart, setDateStart] = useState("");
  const [dateEnd, setDateEnd] = useState("");
  const [timeStart, setTimeStart] = useState("");
  const [timeEnd, setTimeEnd] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

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
    // backend
    // po sukcesie:
    // navigate("/ads");
  };

  return (
    <div className="form-container">
      <h2>Create New Sport Ad</h2>
      <form
        onSubmit={handleSubmit}
        style={{
          display: "flex",
          flexDirection: "column",
          gap: "1rem",
          maxWidth: "500px",
        }}
      >
        <input
          type="text"
          placeholder="Ad title"
          value={title}
          required
          onChange={(e) => setTitle(e.target.value)}
        />

        <textarea
          placeholder="Description (optional)"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />

        <select
          value={sportTypeId}
          required
          onChange={(e) => setSportTypeId(e.target.value)}
        >
          <option value="">Select sport</option>
          <option value="1">Football</option>
          <option value="2">Basketball</option>
          <option value="3">Tennis</option>
          {/* backend */}
        </select>

        <input
          type="text"
          placeholder="Location"
          value={location}
          required
          onChange={(e) => setLocation(e.target.value)}
        />

        <input
          type="number"
          placeholder="Number of participants"
          value={participants}
          min={1}
          required
          onChange={(e) => setParticipants(parseInt(e.target.value))}
        />

        <label>Start Date:</label>
        <input
          type="date"
          value={dateStart}
          required
          onChange={(e) => setDateStart(e.target.value)}
        />

        <label>End Date:</label>
        <input
          type="date"
          value={dateEnd}
          required
          onChange={(e) => setDateEnd(e.target.value)}
        />

        <label>Start Time:</label>
        <input
          type="time"
          value={timeStart}
          required
          onChange={(e) => setTimeStart(e.target.value)}
        />

        <label>End Time:</label>
        <input
          type="time"
          value={timeEnd}
          required
          onChange={(e) => setTimeEnd(e.target.value)}
        />

        <button type="submit">Publish</button>
      </form>
    </div>
    // trzeba dorobić plik css do tego
  );
}
