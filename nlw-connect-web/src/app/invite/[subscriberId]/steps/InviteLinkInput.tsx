"use client"

import IconButton from "@/components/IconButton"
import Input from "@/components/Input"
import { Copy, Link } from "lucide-react"
import { toast } from "react-toastify"

const { InputRoot, InputIcon, InputField } = Input

interface InviteLinkInputProps {
  inviteLink: string
}

function InviteLinkInput({ inviteLink }: InviteLinkInputProps) {
  function copyInviteLink() {
    navigator.clipboard.writeText(inviteLink)

    toast.success("Copied to clipboard successfully!")
  }

  return (
    <InputRoot>
      <InputIcon>
        <Link className="size-5" />
      </InputIcon>

      <InputField
        readOnly
        defaultValue={inviteLink}
      />

      <IconButton
        className="-mr-2"
        onClick={copyInviteLink}
      >
        <Copy className="size-5" />
      </IconButton>
    </InputRoot>
  )
}

export default InviteLinkInput
