package com.krishna.TransactionProcessor;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class AccountController {
    private final AccountRepository repository;

    AccountController(AccountRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> processTransactions(@RequestBody Map<String, Object>[] transactions) {
        List<Map<String, Object>> list = new ArrayList<>();

        for (Map<String, Object> json : transactions) {
            String cmd = (String) json.get("cmd");
            if (cmd.equals("DEPOSIT")) {
                String accountId = (String) json.get("accountId");
                int amt = (int) json.get("amount");
                double amount = amt;
                Account account = repository.findById(accountId).orElse(new Account(accountId, 0));
                if (account.isFrozen() || amount < 0) {
                    list.add(json);
                    continue;
                }
                account.setBalance(amount);
                repository.save(account);
                continue;
            }
            if (cmd.equals("WITHDRAW")) {
                String accountId = (String) json.get("accountId");
                int bal = (int) json.get("amount");
                double amount = bal;
                Account account = repository.findById(accountId).orElse(new Account(accountId, 0));
                if (account.isFrozen() || amount > account.getBalance() || account.getBalance() < 0) {
                    list.add(json);
                    continue;
                }
                account.setBalance(amount);
                repository.save(account);
                continue;
            } else if (cmd.equals("XFER")) {
                String fromId = (String) json.get("fromId");
                String toId = (String) json.get("toId");
                int bal = (int) json.get("amount");
                double amount = bal;
                Account accountFrom = repository.findById(fromId).orElse(new Account(fromId, 0));
                Account accountTo = repository.findById(toId).orElse(new Account(fromId, 0));
                if (accountFrom.isFrozen() || accountTo.isFrozen() || (accountFrom.getBalance() < amount)) {
                    list.add(json);
                    continue;
                }
                addBalance(fromId, amount);
                deductBalance(toId, amount);
                continue;
            } else if (cmd.equals("FREEZE")) {
                String accountId = (String) json.get("accountId");
                Account account = repository.findById(accountId).orElse(new Account(accountId, 0));
                account.setFrozen(true);
                repository.save(account);
                continue;
            } else if (cmd.equals("THAW")) {
                String accountId = (String) json.get("accountId");
                Account account = repository.findById(accountId).orElse(new Account(accountId, 0));
                account.setFrozen(false);
                repository.save(account);
                continue;
            } else {
                list.add(json);
            }
        }
        return list;
    }

    public void addBalance(String accountId, double amount) {
        Account accountFrom = repository.findById(accountId).orElse(new Account(accountId, 0));
        accountFrom.setBalance(accountFrom.getBalance() - amount);
        repository.save(accountFrom);
    }

    public void deductBalance(String accountId, double amount) {
        Account accountTo = repository.findById(accountId).orElse(new Account(accountId, 0));
        accountTo.setBalance(accountTo.getBalance() + amount);
        repository.save(accountTo);
    }

    @GetMapping("/accounts")
    @ResponseBody
    public List<Account> getAccounts(@RequestParam List<String> accountId) {
        List<Account> accounts = new ArrayList<>();
        for (String id : accountId) {
            Account account = repository.findById(id).orElse(new Account(id, 0));
            accounts.add(account);
        }
        return accounts;
    }
}


