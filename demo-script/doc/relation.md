
-- 统计op对应的请求数量
grep response test.log | awk -F'`' '{ split($9,a,"="); ops[a[2]]++; } END{for(op in ops) print "op:",op,"count:",ops[op] }'