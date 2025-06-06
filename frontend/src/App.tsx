import { useEffect, useRef, useState } from "react";
// Images from freepik.com
import basketball from "./assets/basketball.jpg";
import football from "./assets/football.jpg";
import tennis from "./assets/tennis.jpg";
import "./App.css";

const images = [basketball, football, tennis] as const;

function App() {
  const [currentSlide, setCurrentSlide] = useState(0);
  const slideTime = 5000;
  const slideInterval = useRef(0);

  useEffect(() => {
    slideInterval.current = setInterval(() => {
      setCurrentSlide((currentSlide + 1) % images.length);
    }, slideTime);

    return () => {
      clearInterval(slideInterval.current);
    };
  });
  return (
    <main>
      <div className="stack fit">
        {images.map((image, index) => {
          const className = (() => {
            if (index === currentSlide) return "image active";
            else return "image";
          })();
          return <img id={`titleImage${index}`} key={index} className={className} src={image} />;
        })}
        <div className="textbox top">
          <h1>Sports Matcher</h1>
          <span>Bored? Find people to play sports games with!</span>
          <div className="flex-horizontal">
            <button>Find sports</button>
          </div>
        </div>
      </div>
    </main>
  );
}

export default App;
