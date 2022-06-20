# Contributing Guidelines

## Set up testing environment

1. Copy `config.properties.example` to `config.properties`
2. Change the values provided inside
3. Copy the absolute path to your `config.properties` file and set the `visol.config.location` environment variable in the `Run Tomcat Server` to that absolute path

Alternatively, you can manually set the following environment variables:
- `visol.config.database.url`
- `visol.config.database.name`
- `visol.config.database.production_schema`
- `visol.config.database.test_schema`
- `visol.config.database.username`
- `visol.config.database.password`

## Committing

Use an appropriate [gitmoji](https://gitmoji.dev/) as the first character in your commit. [This InteliJ extension](https://intellij.patou.dev/) can
help.

The rest of the commit message is lowercase, without a period at the end, and in present simple.

**For example**: `📝 create contributing guidelines`, not `Created contributing guidelines`.

## Branches

Lower case, dash separated. Named after a feature.

If you consider the branch yours, private (you might force push) the name shall start with your name.

**For example**: `drag-and-drop` or `luka-post-schedule`.

## Conflict resolution strategy

Use merger over rebase in all but your private branches.


