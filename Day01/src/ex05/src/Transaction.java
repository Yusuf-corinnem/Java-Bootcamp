import java.util.UUID;

public class Transaction {
    public enum Category {
        DEBIT,
        CREDIT
    }
    private UUID id;
    private User recipient;
    private User sender;
    private Category transferCategory;

    private Integer transferAmount;

    Transaction(UUID id, User recipient, User sender, Category transferCategory, Integer transferAmount) {
        this.id = id;
        if (recipient.equals(sender)) System.exit(-1);
        this.recipient = recipient;
        this.sender = sender;
        this.transferCategory = transferCategory;
        if (transferCategory == Category.DEBIT) {
            if (transferAmount > 0) this.transferAmount = transferAmount;
            else System.exit(-1);
        }
        else if (transferCategory == Category.CREDIT){
            if (transferAmount < 0) this.transferAmount = transferAmount;
            else System.exit(-1);
        }
    }

    public UUID getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public Category getTransferCategory() {
        return transferCategory;
    }

    public Integer getTransferAmount() {
        return transferAmount;
    }
}
