import "./App.css";
import ImageSlideshow from "./ImageSlideshow";
import Header from "./components/Header";
import { useNavigate } from "react-router-dom";


function App() {
  const navigate = useNavigate();
  return (
    <>
      <Header />
      <main>
        <div className="stack fit">
          <ImageSlideshow />
          <div className="textbox top">
            <h1>Sports Matcher</h1>
            <span>Bored? Find people to play sports games with!</span>
            <div className="flex-horizontal">
              <button onClick={() => navigate("/ads")}> Find Sports</button>
            </div>
          </div>
        </div>
    </main>
    </>
  );
}

export default App;
