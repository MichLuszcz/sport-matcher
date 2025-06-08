import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import "./AdPage.css";

type Ad = {
  id: number;
  title: string;
  description: string;
  location: string;
  eventDateTime: string;
  maxParticipants: number;
  sportType: {
    id: number;
    name: string;
  };
  user: {
    id: number;
    username: string;
  };
};


export default function AdPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [ad, setAd] = useState<Ad | null>(null);

  useEffect(() => {
    // backend
  }, [id]);

  const formatDate = (iso: string) =>
    new Date(iso).toLocaleDateString(undefined, {
      weekday: "long",
      year: "numeric",
      month: "short",
      day: "numeric",
    });

  const formatTime = (iso: string) =>
    new Date(iso).toLocaleTimeString(undefined, {
      hour: "2-digit",
      minute: "2-digit",
    });

  const handleJoin = () => {
    navigate(`/ads/${id}/joined`);
  };

  if (!ad) {
    return <div className="adpage-container">Ad not found.</div>;
  }

  return (
    <div className="adpage-container">
      <button className="adpage-back-button" onClick={() => navigate(-1)}>← Back</button>

      <div className="adpage-card">
        <h2 className="adpage-title">{ad.title}</h2>

        <div className="adpage-meta">
          <span className="ad-sport">{ad.sportType.name}</span>
          <span className="ad-separator">|</span>
          <span className="ad-participants">
            <img
              src="https://img.icons8.com/ios-filled/24/ffffff/user.png"
              alt="Participants"
              className="user-icon"
            />
            {ad.maxParticipants}
          </span>
        </div>

        <p className="adpage-description">{ad.description}</p>

        <div className="adpage-info">
          <p>
            <strong>Where:</strong> {ad.location}
          </p>
          <p>
            <strong>When:</strong> {formatDate(ad.eventDateTime)} at {formatTime(ad.eventDateTime)}
          </p>
        </div>

        <div className="adpage-footer">
          <p>Author: {ad.user.username}</p>
        </div>

        <button className="adpage-join-button" onClick={handleJoin}>Join this event</button>
      </div>
    </div>
  );
}
