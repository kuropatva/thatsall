# Server initiated
### Refresh
`REF [stateHash] [json]` \
Client updates only when stateHash is different
### Round finish
`FNR [json]`
### Game finish (somebody won)
`FNG [nickname]`
### Error
`ERR [message]`

### Message

`MSG [message]`

# Client initiated
### Update
`UPT` \
Server answers with REF

### Play value card
`PLV [card_id]`

### Play power card
`PLP [card_id]`