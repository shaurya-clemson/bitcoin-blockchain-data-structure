# Bitcoin-Blockchain-Data-Structure

Problem Statement:


### 1. Description
Implement Bitcoin blockchain data structure. This will be done in the following steps:

* Define six users – requires creating EC public key pairs for six different users.
Can be done either in a program using OpenSSL or in a program using OpenSSL
libraries. Requires: understanding EC key generation and storage.

* Create transactions (See Appendix 1 for the minimal requirements on data
structure of transaction) between the 6 users. Start initially with one user having
all the coins (e.g., 100 coins), which are then transferred to other users and then
traded between users. Requires: Understanding BTC transaction data structures,
EC encryption/decryption and EC signatures.

  - A new pub/priv key pair is needed for each transaction.
  - When a sender spends 5 out of 10 coins received from a previous transaction,
the sender itself needs to create another pub/priv key pair to receive the
change of the rest 5 coins.

* Create a Merkle tree (see Appendix 2) for the transactions. What a Merkle tree is
and how to create one is covered in class. Requires: Hashing and DSA signatures.

* Create the genesis block.

* Add a set of three blocks (see Appendix 2 for the minimal requirements of data
structure of block) to the genesis block, with each block containing 4 transactions.
o For simplicity, assume all the transactions are single-input-single-output
transactions.

* Provide within the program verification that each cryptographic primitive is being
done correctly.

### 2. Output

* As the transaction occurring and blocks being created, display all three blocks and
transactions contained in each block.
  - For each block, display all the required fields.
  - For each transaction, display all the required fields.

* The balance of each account after the 12 transactions. You can hardcode the
transaction information/data in your code.

