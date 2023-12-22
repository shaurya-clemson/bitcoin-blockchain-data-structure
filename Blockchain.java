package org.example;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.*;

public class Blockchain {
    private final List<Block> chain;
    private final List<Transaction> currentTransactions;
    private KeyPair userKeyPair;
    private final Map<String, Double> userBalances;

    public Blockchain() {
        this.chain = new ArrayList<>();
        this.currentTransactions = new ArrayList<>();
        this.userBalances = new HashMap<>();
        generateKeyPair(); // Key pair for a user
        createBlock(100, "Genesis"); // Genesis block
    }

    private void updateBalances(Transaction transaction) {
        userBalances.merge(transaction.getSenderName(), transaction.getSender().getBalance() - transaction.getAmount(), Double::sum);
        userBalances.merge(transaction.getReceiverName(), transaction.getReceiver().getBalance() + transaction.getAmount(), Double::sum);
    }

    public void createTransaction(User sender, User recipient, double amount) {
        Transaction transaction = new Transaction(sender, recipient, amount, getLastTransactionHash(), userKeyPair.getPrivate());
        updateBalances(transaction);
        currentTransactions.add(transaction);
    }


    public void createBlock(int proof, String previousBlockHeaderHash) {
        Block block = new Block(chain.size(), System.currentTimeMillis(), new ArrayList<>(currentTransactions), proof, previousBlockHeaderHash);
        currentTransactions.clear();
        chain.add(block);
    }

    private void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
            keyGen.initialize(ecSpec, new SecureRandom());
            this.userKeyPair = keyGen.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String hashBlock(Block block) {
        String data = block.index + block.timestamp + block.proof + block.previousBlockHeaderHash + block.merkleRoot;  // Removed block.transactions.toString()
        return MerkleTree.calculateHash(data);
    }

    public int proofOfWork(int lastProof) {
        int proof = 0;
        while (!validProof(lastProof, proof)) {
            proof++;
        }
        return proof;
    }

    private boolean validProof(int lastProof, int proof) {
        String guess = lastProof + String.valueOf(proof);
        String guessHash = MerkleTree.calculateHash(guess);
        return guessHash.startsWith("0000");
    }

    private String getLastTransactionHash() {
        return chain.isEmpty() ? "genesis" : chain.get(chain.size() - 1).merkleRoot;
    }

    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();

        User shriram = new User("Shriram");
        User sekar = new User("Sekar");
        User shaurya = new User("Shaurya");
        User raghuvanshi = new User("Raghuvanshi");
        User bruno = new User("Bruno");

        // Initial distribution: Shriram has 100 coins
        shriram.addToBalance(100);

        for (int blockIndex = 0; blockIndex < 4; blockIndex++) {
            // Transaction 1: Shriram sends 50 coins to Sekar
            blockchain.createTransaction(shriram, sekar, 50);

            // Transaction 2: Sekar sends 25 coins to Shaurya
            blockchain.createTransaction(sekar, shaurya, 25);

            // Transaction 3: Shaurya sends 20 coins to Raghuvanshi
            blockchain.createTransaction(shaurya, raghuvanshi, 20);

            // Transaction 4: Raghuvanshi sends 15 coins to Bruno
            blockchain.createTransaction(raghuvanshi, bruno, 15);


            int lastProof = blockchain.chain.get(blockchain.chain.size() - 1).proof;
            int proof = blockchain.proofOfWork(lastProof);
            blockchain.createBlock(proof, blockchain.hashBlock(blockchain.chain.get(blockchain.chain.size() - 1)));

            // Display the blockchain
            Block block = blockchain.chain.get(blockIndex);
            System.out.println("Block " + block.index + " Header:");
            for (int index = 0; index < block.transactions.size(); index++) {
                Transaction transaction = block.transactions.get(index);
                System.out.println("Transaction " + (index + 1) + " : " + transaction);
            }
            System.out.println("Proof: " + block.proof);
            System.out.println("Previous Hash: " + block.previousBlockHeaderHash);
            System.out.println("Merkle Root: " + block.merkleRoot);
            System.out.println();
        }

        // Display user balances
        System.out.println("User Balances:");
        for (Map.Entry<String, Double> entry : blockchain.userBalances.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " coins");
        }
    }

}
