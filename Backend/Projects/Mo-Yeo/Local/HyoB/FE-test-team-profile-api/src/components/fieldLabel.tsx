export default function FieldLabel({
  label,
  required = false,
}: {
  label: string;
  required?: boolean;
}) {
  return (
    <label className="text-[14px] font-semibold leading-5 text-[#314158]">
      {label}
      {required && <span className="ml-1 text-[#3B82F6]">*</span>}
    </label>
  );
}
