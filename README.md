# ECSE-223
This repository is a copy from the ECSE 223 Model Based Programming Group Project. The original project repository was set to private access.

**Guide**

*You may ignore steps 1 and 2 since we do not really have tasks and issues yet*

1. Go to the ["Issues" tab on GitHub](https://github.com/W2019-ECSE223/ecse223-group-project-p-6/issues).
2. Look for an issue to fix or create your own issue (press "New issue").
    1. If you are fixing another person's issue:
        1. You can leave a comment if you have questions for the guy (or girl) who created the issue.
        2. Go directly to step 3.
    2. If you are creating your own issue:
        1. You must add the label/milestone sprint1, sprint2, or sprint3. Otherwise, the TA won't be able to check our issues.
        2. Also, add a priority label if necessary:
            1. If it needs to be fixed in the next 24 hours and it's really important, use the "Priority 1 [Urgent!]" label.
            2. If it's really important but we have time, use the "Priority 2" label.
            3. If it's somewhat important, use the "Priority 3" label.
        2. You can add other labels like "bug", "help wanted", or "question" to make your issue more helpful to others.
3. Create branch: `git branch <branch-name>`
4. Go to branch: `git checkout <branch-name>`
5. **Fix the issue (this is the step where you actually make changes to the code!).**
6. Commit your code:
    1. Add to commit: `git add <filename>` --> PLEASE DO NOT USE `git add .` or `git add *`
    2. `git status` and check that the proper files are green 
    3. To undo step 1: `git reset` 
    4. Commit: `git commit -m "Commit Message"`
        1. In the `"Commit Message"`, you can automatically close an issue by including the phrase `closes #4` (in this case `#4` is the ID number of the issue, you can find this number in the "Issues" tab on GitHub).
        2. Aside from `closes`, you can use the keywords: `close`, `closed`, `fix`, `fixes`, `fixed`, `resolve`, `resolves`, or `resolved`. It can be capitalized too.
7. Push your code to online master branch: `git push -u origin <your-branch-name>` (DO NOT push from your local master branch).
8. Go to the ["Pull requests" tab on GitHub](https://github.com/W2019-ECSE223/ecse223-group-project-p-6/pulls). Press the "Create pull request" button for your branch.
9. Approve your pull request in "Pull requests" tab.
12. If you didn't automatically close the issue you were trying to fix in step 6.1.1, you can do that manually right now. Go to the "Issues" tab, click the issue you fixed, then press the "Close issue" button.
13. Switch to master branch: `git checkout master`
14. Pull from master branch: `git pull origin master`
