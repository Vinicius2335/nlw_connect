"use client"

import Button from "@/components/Button"
import Input from "@/components/Input"
import { createSubscription } from "@/http/api"
import { zodResolver } from "@hookform/resolvers/zod"
import { ArrowRight, Mail, User } from "lucide-react"
import { useRouter, useSearchParams } from "next/navigation"
import React from "react"
import { useForm } from "react-hook-form"
import { z } from "zod"

const { InputRoot, InputField, InputIcon } = Input

const subscriptionSchema = z.object({
  name: z.string().min(2, "Digite seu nome completo"),
  email: z.string().email("Digite um e-mail válido")
})

type SubscriptionSchema = z.infer<typeof subscriptionSchema>

function SubscriptionForm() {
  const router = useRouter()
  const searchParams = useSearchParams()

  const {
    register,
    handleSubmit,
    formState: { errors }
  } = useForm<SubscriptionSchema>({
    resolver: zodResolver(subscriptionSchema)
  })

  async function onSubscribe({ email, name }: SubscriptionSchema) {
    const prettyName = "codecraft-summit-2025"
    const referrerParam = searchParams.get("referrer")
    let response: number | undefined

    console.log(referrerParam)

    try {
      if (!referrerParam) {
        const { subscriberId } = await createSubscription(prettyName, { email, name })
        response = subscriberId
      } else {
        const { subscriberId } = await createSubscription(
          prettyName,
          { email, name },
          { referrer: Number(referrerParam) }
        )
        response = subscriberId
      }

      if (response === undefined) {
        throw new Error("subscriberId não foi encontrado na resposta")
      }
    } catch (ex) {
      console.error(ex)
      alert("Error while try subscription user")
    }

    router.push(`/invite/${response}`)
  }

  return (
    <form
      onSubmit={handleSubmit(onSubscribe)}
      className="bg-gray-700 border border-gray-600 rounded-2xl p-8 space-y-6 w-full md:max-w-[440px]"
    >
      <h2 className="font-heading font-semibold text-gray-200 text-xl">Inscrição</h2>

      <div className="space-y-3">
        <div className="space-y-2">
          <InputRoot>
            <InputIcon>
              <User className="size-6" />
            </InputIcon>

            <InputField
              type="text"
              placeholder="Nome Completo"
              {...register("name")}
            />
          </InputRoot>

          {errors.name && (
            <p className="text-danger text-xs font-semibold">{errors.name.message}</p>
          )}
        </div>

        <div className="space-y-2">
          <InputRoot>
            <InputIcon>
              <Mail className="size-6" />
            </InputIcon>

            <InputField
              type="email"
              placeholder="Email Completo"
              {...register("email")}
            />
          </InputRoot>

          {errors.email && (
            <p className="text-danger text-xs font-semibold">{errors.email.message}</p>
          )}
        </div>
      </div>

      <Button type="submit">
        Confirmar
        <ArrowRight className="size-6" />
      </Button>
    </form>
  )
}

export default SubscriptionForm
