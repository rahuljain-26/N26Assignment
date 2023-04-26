import csv

transactions = {}
with open('transactions.csv') as file:
    filereader = csv.DictReader(file)
    for row in filereader:
        transactions[row['transaction_id']] = row
   # print(transactions)

users = {}
with open('users.csv') as file:
    filereader = csv.DictReader(file)
    for row in filereader:
        users[row['user_id']] = row
    # print (users)

# Compute the result of the query
results = {}
for transaction_id, transaction in transactions.items():
    if transaction['is_blocked'] == 'False':
        user_id = transaction['user_id']
        if users[user_id]['is_active'] == '1':
            category_id = transaction['transaction_category_id']
            amount = transaction['amount']
            if category_id not in results:
                results[category_id] = {'sum_amount': 0, 'num_users': set()}
            results[category_id]['sum_amount'] += amount
            results[category_id]['num_users'].add(user_id)

# Print the result
for category_id, result in sorted(results.items(), key=lambda x: x[1]['sum_amount'], reverse=True):
    sum_amount = result['sum_amount']
    num_users = len(result['num_users'])
    print(f"{category_id}, {sum_amount:.2f}, {num_users}")
