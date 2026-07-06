function Textarea({
  ...props
}: React.TextareaHTMLAttributes<HTMLTextAreaElement>) {
  return (
    <textarea
      {...props}
      className="bg-white w-full px-4 py-[14px] h-27 shrink-0 rounded-lg border border-[#E2E8F0] focus:outline-none text-[14px] resize-none"
    />
  );
}

export default Textarea;
