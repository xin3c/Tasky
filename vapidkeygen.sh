#!/bin/bash

touch .vapid

openssl ecparam -name prime256v1 -genkey -noout -out vapid_private.pem 2>/dev/null
openssl ec -in vapid_private.pem -pubout -out vapid_public.pem 2>/dev/null

private_der=$(openssl ec -in vapid_private.pem -outform DER 2>/dev/null | tail -c +8 | base64 | tr '/+' '_-' | tr -d '=')
public_der=$(openssl ec -in vapid_private.pem -pubout -outform DER 2>/dev/null | tail -c +28 | base64 | tr '/+' '_-' | tr -d '=')

echo "vapid.public.key=$public_der" > .vapid
echo "vapid.private.key=$private_der" >> .vapid

echo "Keys are generated and stored in .vapid file"

cat .vapid

rm vapid_public.pem
rm vapid_private.pem
