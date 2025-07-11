import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./AdList.css";

type Ad = {
  id: string;
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

export default function AdList() {
  const navigate = useNavigate();
  const [ads, setAds] = useState<Ad[]>([
    
  ]);

  const [sport, setSport] = useState("");
  const [day, setDay] = useState("");
  const [time, setTime] = useState("");
  const [search, setSearch] = useState("");

  const formatDate = (iso: string) => {
    const date = new Date(iso);
    return date.toLocaleDateString(undefined, {
      weekday: "long",
      year: "numeric",
      month: "short",
      day: "numeric",
    });
  };

  const formatTime = (iso: string) => {
    const date = new Date(iso);
    return date.toLocaleTimeString(undefined, {
      hour: "2-digit",
      minute: "2-digit",
    });
  };

  const filteredAds = ads.filter((ad) => {
    const matchesSearch =
      ad.description.toLowerCase().includes(search.toLowerCase()) ||
      ad.sportTypeName.toLowerCase().includes(search.toLowerCase());

    const adDate = new Date(ad.eventDateTime);
    const adDay = adDate.toLocaleDateString(undefined, { weekday: "long" });
    const adTime = adDate.toTimeString().slice(0, 5);

    return (
      matchesSearch &&
      (sport === "" || ad.sportTypeName === sport) &&
      (day === "" || adDay === day) &&
      (time === "" || adTime === time)
    );
  });

  const clearFilters = () => {
    setSport("");
    setDay("");
    setTime("");
    setSearch("");
  };

type AdResponse = {
  _embedded: {
    ads: Ad[];
  };
};


  async function fetchData() {
    try {
      const response = await fetch(`${import.meta.env.VITE_API_URL}/ads`, {
      headers: {
        "Authorization": `Bearer ${localStorage.getItem("accessToken")}`
      }
      })
      // type AdResponse = Ad[] | { ads: Ad[] };
      const result: AdResponse = await response.json();
      console.log(result);
      console.log("-----");
      const ads = result._embedded?.ads ?? [];
      console.log(ads)
      setAds(ads);
      // console.log(ads);
      return ads;
      // if (Array.isArray(result)) {
      //   setAds(result)
      // } else if (Array.isArray(result.ads)) {
      //   setAds(result.ads);
      // } else {
      //   console.error("Unexpected response format:", result);
      // }
      } catch (error) {
        if (error instanceof Error) {
          console.error("An error occured while fetching ads:\n" + error)
        }
      }
  }

  useEffect(() => {
    fetchData()
  }, [])

  return (
    <div className="adlist-container">
      <div className="adlist-header">
        <h2 className="adlist-title">Sports Matcher</h2>

        <div className="ad-actions-centered">
          <button className="action-button" onClick={() => navigate("/ads/new")}>
            Create New Ad
          </button>
          <button className="action-button" onClick={() => navigate("/profile")}>
            My Profile
          </button>
        </div>
      </div>

      <div className="adlist-filters-bar">
        <input
          type="text"
          placeholder="Search by sport or description..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="search-input"
        />

        <div className="adlist-filters">
          <select value={sport} onChange={(e) => setSport(e.target.value)}>
            <option value="">All sports</option>
            <option value="Football">Football</option>
            <option value="Basketball">Basketball</option>
            <option value="Tennis">Tennis</option>
          </select>

          <select value={day} onChange={(e) => setDay(e.target.value)}>
            <option value="">Any day</option>
            <option value="Monday">Monday</option>
            <option value="Monday">Monday</option>
            <option value="Wednesday">Wednesday</option>
            <option value="Monday">Monday</option>
            <option value="Friday">Friday</option>
            <option value="Monday">Monday</option>
            <option value="Monday">Monday</option>
          </select>

          <input
            type="time"
            value={time}
            onChange={(e) => setTime(e.target.value)}
          />

          <button onClick={clearFilters}>Clear filters</button>
        </div>
      </div>

      <ul className="adlist-list">
        {filteredAds.length === 0 && <p>No ads match the selected filters.</p>}
        {filteredAds.map((ad) => (
          <li
            key={ad.id}
            className="ad-card"
            onClick={() => {
              console.log(ad);
              navigate(`/ads/${ad.id}`)}}
            style={{ cursor: "pointer" }}
          >
            <h3 className="ad-title">{ad.title}</h3>
            <div className="ad-meta">
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

            <p className="ad-description">{ad.description}</p>

            <div className="ad-info">
              <p><strong>Where:</strong> {ad.location}</p>
              <p><strong>When:</strong> {formatDate(ad.eventDateTime)} at {formatTime(ad.eventDateTime)}</p>
            </div>

            <div className="ad-footer">
              <p>Author: {ad.username}</p>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}
