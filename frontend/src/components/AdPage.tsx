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

const mockAds: Ad[] = [
 {
      id: 1,
      title: "Ad 1",
      description: "Description 1",
      location: "Location 1",
      eventDateTime: "2025-06-10T18:00:00",
      maxParticipants: 10,
      sportType: {
        id: 1,
        name: "Football",
      },
      user: {
        id: 1,
        username: "author1",
      },
    },
    {
      id: 2,
      title: "Ad 2",
      description: "Description 2",
      location: "Location 2",
      eventDateTime: "2025-06-10T18:00:00",
      maxParticipants: 10,
      sportType: {
        id: 1,
        name: "Football",
      },
      user: {
        id: 1,
        username: "author2",
      },
    },
    {
      id: 3,
      title: "Ad 3",
      description: "Description 3",
      location: "Location 3",
      eventDateTime: "2025-06-10T18:00:00",
      maxParticipants: 10,
      sportType: {
        id: 1,
        name: "Football",
      },
      user: {
        id: 1,
        username: "author3",
      },
    },
];

export default function AdPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [ad, setAd] = useState<Ad | null>(null);

  useEffect(() => {
    // backend
    const found = mockAds.find((a) => a.id === Number(id));
    setAd(found || null);
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
