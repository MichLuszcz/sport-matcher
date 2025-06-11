import {useParams, useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import "./AdPage.css";

interface Ad {
  id: number;
  title: string;
  description: string;
  location: string;
  eventDateTime: string;
  maxParticipants: number;
  sportTypeId: number,
  sportTypeName: string;
  userId: number,
  username: string;
};


export default function AdPage() {
  const {id} = useParams();
  const navigate = useNavigate();
  const [ad, setAd] = useState<Ad | null>(null);

  async function fetchData() {
    try {
      const response = await fetch(`${import.meta.env.VITE_API_URL}/ads/${id}`, {
        headers: {
          "Authorization": `Bearer ${localStorage.getItem("accessToken")}`
        }
      })
      // type AdResponse = Ad[] | { ads: Ad[] };
      const result: Ad = await response.json();
      console.log(result)
      setAd(result);
      console.log(result);
    } catch (error) {
      if (error instanceof Error) {
        console.error("An error occured while fetching ad:\n" + error)
      }
    }
  }

  useEffect(() => {
    fetchData()
  }, [])

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
          <span className="ad-sport">{ad.sportTypeName}</span>
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
          <p>Author: {ad.username}</p>
        </div>

        <button className="adpage-join-button" onClick={handleJoin}>Join this event</button>
      </div>
    </div>
  );
}
