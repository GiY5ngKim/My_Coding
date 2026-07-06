function Input({ ...props }: React.InputHTMLAttributes<HTMLInputElement>) {
  return (
    <input
      {...props}
      className="bg-white w-full px-4 py-[14px] rounded-lg border border-[#E2E8F0] focus:outline-none text-[14px]"
    />
  );
}

export default Input;
