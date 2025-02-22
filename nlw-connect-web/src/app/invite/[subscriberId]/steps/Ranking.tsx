import medalCopperImg from "@/assets/medal-cooper.svg"
import medalGoldImg from "@/assets/medal-gold.svg"
import medalSilverImg from "@/assets/medal-silver.svg"
import { type SubscriptionRankingItem, generateRankingByEvent } from "@/http/api"
import Image from "next/image"

async function Ranking() {
  const prettyName = "codecraft-summit-2025"
  // gera o top 3 ranking
  const ranking = await generateRankingByEvent(prettyName)

  return (
    <div className="w-full max-w-[440px] space-y-5">
      <h2 className="text-gray-200 text-xl font-heading font-semibold leading-none">
        Ranking de Indicações
      </h2>

      {ranking.map((item, index) => {
        return (
          <div
            className="space-y-4"
            key={item.userName}
          >
            <div className="relative rounded-xl bg-gray-700 border border-gray-600 p-6 flex flex-col justify-center gap-3">
              <span className="text-sm text-gray-300 leading-none">
                <span className="font-semibold">1⁰</span> | {item.userName}
              </span>
              <span className="font-heading text-2xl font-semibold text-gray-200 leading-none">
                {item.subscribers}
              </span>

              {index === 0 && (
                <Image
                  className="absolute top-0 right-8"
                  src={medalGoldImg}
                  alt="Image Gold Medal"
                />
              )}

              {index === 1 && (
                <Image
                  className="absolute top-0 right-8"
                  src={medalSilverImg}
                  alt="Image Silver Medal"
                />
              )}

              {index === 2 && (
                <Image
                  className="absolute top-0 right-8"
                  src={medalCopperImg}
                  alt="Image Copper Medal"
                />
              )}
            </div>
          </div>
        )
      })}
    </div>
  )
}

export default Ranking
