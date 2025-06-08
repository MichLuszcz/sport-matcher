import "./RequestSent.css";
import { useNavigate } from "react-router-dom";

export default function RequestSent() {
  const navigate = useNavigate();

  return (
    <div className="request-sent-container">
      <h1 className="request-sent-title">You succesfully requested to join this event!</h1>
      <p className="request-sent-message">
        Wait for ad's author response.
      </p>
      <button
        className="request-sent-button"
        onClick={() => navigate("/ads")}>← Go back to the Search Page</button>
    </div>
  );
}