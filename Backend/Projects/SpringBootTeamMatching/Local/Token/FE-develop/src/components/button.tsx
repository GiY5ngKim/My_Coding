import React from 'react';

// 버튼의 타입을 정의합니다.
interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: 'primary' | 'secondary' | 'outline'; // 버튼 스타일 종류
  size?: 'sm' | 'md' | 'lg'; // 버튼 크기
  fullWidth?: boolean; // 가로 꽉 채우기 여부
  isLoading?: boolean; // 로딩 상태 유무
}

const Button: React.FC<ButtonProps> = ({
  children,
  variant = 'primary',
  size = 'md',
  fullWidth = true,
  isLoading = false,
  className = '',
  ...props
}) => {
  // 1. 기본 스타일
  const baseStyles = "flex items-center justify-center font-bold transition-all active:scale-[0.98] disabled:opacity-50 disabled:cursor-not-allowed";
  
  // 2. 배경 및 글자색 변형(Variant)
  const variants = {
    primary: "bg-[#5C7CFF] text-white shadow-lg shadow-blue-100 hover:bg-blue-600",
    secondary: "bg-blue-50 text-[#5C7CFF] hover:bg-blue-100",
    outline: "border-2 border-[#5C7CFF] text-[#5C7CFF] bg-white hover:bg-blue-50",
  };

  // 3. 크기 변형(Size)
  const sizes = {
    sm: "py-2 px-4 text-sm rounded-[12px]",
    md: "py-4 px-6 text-base rounded-[18px]",
    lg: "py-5 px-6 text-[18px] rounded-[20px]",
  };

  const widthStyle = fullWidth ? "w-full" : "w-auto";

  return (
    <button
      className={`${baseStyles} ${variants[variant]} ${sizes[size]} ${widthStyle} ${className}`}
      disabled={isLoading || props.disabled}
      {...props}
    >
      {isLoading ? (
        <span className="animate-spin mr-2">⏳</span> // 로딩 아이콘 (추후 SVG로 교체 가능)
      ) : null}
      {children}
    </button>
  );
};

export default Button;