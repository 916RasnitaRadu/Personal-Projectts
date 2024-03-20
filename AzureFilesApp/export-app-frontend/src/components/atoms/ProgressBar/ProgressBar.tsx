import React, { useState, useEffect } from "react";
import "./ProgressBar.css";
import LoadingIcon from "../Icons/Loading";

interface ProgressBarProps {
  isRunning: boolean;
  onComplete: () => void;
}

const ProgressBar = (props: ProgressBarProps) => {
  const [filled, setFilled] = useState(0);
  const [isComplete, setIsComplete] = useState(false);

  useEffect(() => {
    if (filled < 100 && props.isRunning) {
      const timeoutId = setTimeout(() => {
        setFilled((prev) => Math.min(prev + 2, 100));
      }, 50);

      return () => clearTimeout(timeoutId);
    } else if (filled === 100 && !isComplete) {
      setIsComplete(true);
      setTimeout(() => {
        setIsComplete(false);
        setFilled(0);
        props.onComplete();
      }, 700);
    }
  }, [filled, props.isRunning, props.onComplete]);

  return (
    <div>
      {props.isRunning && (
        <div className="bg-[#343433] overflow-hidden my-10 w-56 md:w-60 lg:w-64 xl:w-80 2xl:w-[400px]">
          <div className="flex text-xl justify-center">
            Exporting...
            <LoadingIcon />
          </div>
          <div
            className="bg-white mt-2 rounded-lg"
            style={{
              height: "100%",
              width: `${filled}%`,
              transition: "width 0.5s",
            }}
          >
            <span className="text-[#343433] flex justify-center font-medium text-2xl">
              {filled}%
            </span>
          </div>
        </div>
      )}
    </div>
  );
};

export default ProgressBar;
