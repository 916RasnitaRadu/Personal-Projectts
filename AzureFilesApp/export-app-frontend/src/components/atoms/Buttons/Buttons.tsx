import { ReactNode, MouseEventHandler } from "react";

interface ButtonProps {
  text: string;
  method: MouseEventHandler<HTMLButtonElement>;
  children: ReactNode;
}

const Button = ({ text, method, children }: ButtonProps) => {
  return (
    <button
      className="items-center bg-transparent border-none cursor-pointer whitespace-nowrap px-3 py-3 hover:bg-[#4a4a4a] hover:rounded-lg"
      onClick={method}
    >
      <div>{children}</div>
      <span className="block">{text}</span>
    </button>
  );
};

export default Button;
