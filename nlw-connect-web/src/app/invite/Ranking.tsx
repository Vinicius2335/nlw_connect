import medalCopperImg from "@/assets/medal-cooper.svg"
import medalGoldImg from "@/assets/medal-gold.svg"
import medalSilverImg from "@/assets/medal-silver.svg"
import Image from "next/image"

function Ranking() {
  return (
    <div className="w-full max-w-[440px] space-y-5">
      <h2 className="text-gray-200 text-xl font-heading font-semibold leading-none">
        Ranking de Indicações
      </h2>

      <div className="space-y-4">
        <div className="relative rounded-xl bg-gray-700 border border-gray-600 p-6 flex flex-col justify-center gap-3">
          <span className="text-sm text-gray-300 leading-none">
            <span className="font-semibold">1⁰</span> | Vinicius Vieira
          </span>
          <span className="font-heading text-2xl font-semibold text-gray-200 leading-none">
            130
          </span>

          <Image
            className="absolute top-0 right-8"
            src={medalGoldImg}
            alt="Image Gold Medal"
          />
        </div>
      </div>

      <div className="space-y-4">
        <div className="relative rounded-xl bg-gray-700 border border-gray-600 p-6 flex flex-col justify-center gap-3">
          <span className="text-sm text-gray-300 leading-none">
            <span className="font-semibold">2⁰</span> | Vinicius Vieira
          </span>
          <span className="font-heading text-2xl font-semibold text-gray-200 leading-none">
            130
          </span>

          <Image
            className="absolute top-0 right-8"
            src={medalSilverImg}
            alt="Image Silver Medal"
          />
        </div>
      </div>

      <div className="space-y-4">
        <div className="relative rounded-xl bg-gray-700 border border-gray-600 p-6 flex flex-col justify-center gap-3">
          <span className="text-sm text-gray-300 leading-none">
            <span className="font-semibold">3⁰</span> | Vinicius Vieira
          </span>
          <span className="font-heading text-2xl font-semibold text-gray-200 leading-none">
            130
          </span>

          <Image
            className="absolute top-0 right-8"
            src={medalCopperImg}
            alt="Image Copper Medal"
          />
        </div>
      </div>
    </div>
  )
}

export default Ranking
