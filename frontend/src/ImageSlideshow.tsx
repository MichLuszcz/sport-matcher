import basketball from "./assets/basketball.jpg";
import football from "./assets/football.jpg";
import tennis from "./assets/tennis.jpg";
import "./App.css";
import { useEffect, useRef, useState } from "react";

const images = [basketball, football, tennis] as const;

export default function ImageSlideshow() {
  const [currentSlide, setCurrentSlide] = useState(0);
  const slideTime = 5000;
  const slideInterval = useRef(0);

  useEffect(() => {
    console.log("useEffect called!")
    slideInterval.current = setTimeout(() => {
      setCurrentSlide((currentSlide + 1) % images.length);
    }, slideTime);

    return () => {
      clearTimeout(slideInterval.current);
    };
  });

  return (
    <>
      {images.map((image, index) => {
        const className = (() => {
          if (index === currentSlide) return "image active";
          else return "image";
        })();
        return <img id={`titleImage${index}`} key={index} className={className} src={image} />;
      })}
    </>
  );
}
